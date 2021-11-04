package us.zidali.recipeapp.business.datasource.network.main

import retrofit2.http.GET
import retrofit2.http.Query
import us.zidali.recipeapp.business.datasource.network.main.responses.SearchResponse

interface MainService {

    @GET("/search")
    suspend fun search(
        @Query("app_id") app_id: String,
        @Query("app_key") app_key: String,
        @Query("q") query: String,
        @Query("from") from: Int,
        @Query("to") to: Int,
    ): SearchResponse

}