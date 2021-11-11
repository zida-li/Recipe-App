package dev.zidali.recipeapp.interactors.search

import com.google.gson.GsonBuilder
import dev.zidali.recipeapp.business.datasource.cache.main.FavoriteRecipeDao
import dev.zidali.recipeapp.business.datasource.cache.main.toFavoriteEntity
import dev.zidali.recipeapp.business.datasource.network.main.MainService
import dev.zidali.recipeapp.business.domain.models.Recipe
import dev.zidali.recipeapp.business.interactors.main.search.SaveRecipeToFavorite
import dev.zidali.recipeapp.business.interactors.main.search.list.SearchRecipes
import dev.zidali.recipeapp.datasource.cache.AppDatabaseFake
import dev.zidali.recipeapp.datasource.cache.FavoriteRecipeDaoFake
import dev.zidali.recipeapp.datasource.network.main.search.SearchRecipesResponses
import dev.zidali.recipeapp.datasource.network.main.search.SingleDuplicateRecipe
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class SaveRecipeToFavoriteTest {

    private val appDataBase = AppDatabaseFake()
    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl

    //system in test
    private lateinit var saveRecipeToFavorite: SaveRecipeToFavorite

    //dependencies
    private lateinit var mainService: MainService
    private lateinit var cache: FavoriteRecipeDao
    private lateinit var searchRecipes: SearchRecipes

    @BeforeEach
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        baseUrl = mockWebServer.url("/recipe/test/")
        mainService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(MainService::class.java)

        cache = FavoriteRecipeDaoFake(appDataBase)

        saveRecipeToFavorite = SaveRecipeToFavorite(
            favoriteRecipeDao = cache,
        )

        searchRecipes = SearchRecipes(
            favoriteRecipeDao = cache,
            mainService = mainService,
        )

    }

    @Test
    fun success_recipeSaved() = runBlocking {

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(SearchRecipesResponses.success_10Recipes)
        )

        val apiKey = SearchRecipesResponses.apiKey

        val emissions = searchRecipes.execute(
            app_id = apiKey.app_id,
            app_key = apiKey.app_key,
            query = "",
            from = 0,
            to = 10,
        ).toList()

        assert(emissions[0].data?.recipeList?.size == 10)
        assert(emissions[0].data?.recipeList?.get(0) != null)
        assert(emissions[0].data?.recipeList?.get(0) is Recipe)

        //insert a duplicate recipe into cache, to test if SearchRecipe sets isFavorite to true
        var cachedRecipe = cache.getAllRecipes()
        assert(cachedRecipe.isEmpty())

        saveRecipeToFavorite.execute(
            emissions[0].data?.recipeList?.get(0)!!
        ).toList()

        cachedRecipe = cache.getAllRecipes()
        assert(cachedRecipe.size == 1)


    }

}