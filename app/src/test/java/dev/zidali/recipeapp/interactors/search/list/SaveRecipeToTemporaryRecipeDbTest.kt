package dev.zidali.recipeapp.interactors.search.list

import com.google.gson.GsonBuilder
import dev.zidali.recipeapp.business.datasource.cache.main.FavoriteRecipeDao
import dev.zidali.recipeapp.business.datasource.cache.main.search.TemporaryRecipeDao
import dev.zidali.recipeapp.business.datasource.network.main.MainService
import dev.zidali.recipeapp.business.domain.models.Recipe
import dev.zidali.recipeapp.business.interactors.main.search.list.SaveRecipeToTemporaryRecipeDb
import dev.zidali.recipeapp.business.interactors.main.search.list.SearchRecipes
import dev.zidali.recipeapp.datasource.cache.AppDatabaseFake
import dev.zidali.recipeapp.datasource.cache.FavoriteRecipeDaoFake
import dev.zidali.recipeapp.datasource.cache.TemporaryRecipeDaoFake
import dev.zidali.recipeapp.datasource.network.main.search.SearchRecipesResponses
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

class SaveRecipeToTemporaryRecipeDbTest {

    private val appDataBase = AppDatabaseFake()
    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl

    //system in test
    private lateinit var saveRecipeToTemporaryRecipeDb: SaveRecipeToTemporaryRecipeDb

    //dependencies
    private lateinit var searchRecipes: SearchRecipes
    private lateinit var mainService: MainService
    private lateinit var cache1: TemporaryRecipeDao
    private lateinit var cache2: FavoriteRecipeDao

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

        cache1 = TemporaryRecipeDaoFake(appDataBase)

        cache2 = FavoriteRecipeDaoFake(appDataBase)

        searchRecipes = SearchRecipes(
            favoriteRecipeDao = cache2,
            mainService = mainService,
        )

        saveRecipeToTemporaryRecipeDb = SaveRecipeToTemporaryRecipeDb(
            temporaryRecipeDao = cache1
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

        var confirmCacheSize = cache1.getAllRecipes()
        assert(confirmCacheSize.isEmpty())

        saveRecipeToTemporaryRecipeDb.execute(
            recipe = emissions[0].data?.recipeList?.get(0)!!
        ).toList()

        confirmCacheSize = cache1.getAllRecipes()
        assert(confirmCacheSize.isNotEmpty())

        val cache = cache1.getRecipeByLabel(emissions[0].data?.recipeList?.get(0)?.recipeName!!)
        assert(cache?.recipeId == emissions[0].data?.recipeList?.get(0)?.recipeId)

    }



}