package dev.zidali.recipeapp.interactors.search

import com.google.gson.GsonBuilder
import dev.zidali.recipeapp.business.datasource.cache.main.FavoriteRecipeDao
import dev.zidali.recipeapp.business.datasource.network.main.MainService
import dev.zidali.recipeapp.business.interactors.main.search.list.SearchRecipes
import dev.zidali.recipeapp.datasource.cache.AppDatabaseFake
import dev.zidali.recipeapp.datasource.cache.FavoriteRecipeDaoFake
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

class SearchRecipesTest {

    private val appDataBase = AppDatabaseFake()
    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl

    //system in test
    private lateinit var searchRecipes: SearchRecipes

    //dependencies
    private lateinit var mainService: MainService
    private lateinit var favoriteRecipeDao: FavoriteRecipeDao

    @BeforeEach
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        baseUrl = mockWebServer.url("https://api.edamam.com")
        mainService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(MainService::class.java)

        favoriteRecipeDao = FavoriteRecipeDaoFake(appDataBase)

        searchRecipes = SearchRecipes(
            favoriteRecipeDao = favoriteRecipeDao,
            mainService = mainService,
        )

    }

    @Test
    fun success_10Recipes() = runBlocking {

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

        assert(emissions[0].data == null)
        assert(emissions[0].data?.recipeList == null)
//        assert(emissions[0].data?.recipeList?.get(0) != null)
//        assert(emissions[0].data?.recipeList?.get(0) is Recipe)
    }

}