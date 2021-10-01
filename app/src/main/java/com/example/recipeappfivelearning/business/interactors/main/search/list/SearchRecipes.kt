package com.example.recipeappfivelearning.business.interactors.main.search.list

import com.example.recipeappfivelearning.business.datasource.cache.main.FavoriteRecipeDao
import com.example.recipeappfivelearning.business.datasource.cache.main.toFavoriteRecipe
import com.example.recipeappfivelearning.business.datasource.network.main.MainService
import com.example.recipeappfivelearning.business.datasource.network.main.toSearchStateRecipeModel
import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.business.domain.util.DataState
import com.example.recipeappfivelearning.business.domain.util.MessageType
import com.example.recipeappfivelearning.business.domain.util.Response
import com.example.recipeappfivelearning.business.domain.util.UIComponentType
import com.example.recipeappfivelearning.presentation.main.search.list.SearchState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchRecipes (
    private val mainService: MainService,
    private val favoriteRecipeDao: FavoriteRecipeDao,
) {

    fun execute(
        app_id: String,
        app_key: String,
        query: String,
        from: Int,
        to: Int
    ): Flow<DataState<SearchState>> = flow {

        try {
            val recipesFromApi: MutableList<Recipe> = ArrayList()
            val recipesFromFavorites: MutableList<Recipe> = ArrayList()
            val finalRecipeList: MutableList<Recipe> = ArrayList()
            var moreResultsAvailable: Boolean? = null

            val apiCall = mainService.search(
                app_id,
                app_key,
                query,
                from,
                to
            )

            val apiResults = apiCall.recipeHits.map { it.toSearchStateRecipeModel() }

            val cacheCall = favoriteRecipeDao.getAllRecipes().map { it.toFavoriteRecipe() }

            recipesFromApi.addAll(apiResults)
            recipesFromFavorites.addAll(cacheCall)
            moreResultsAvailable = apiCall.moreResultAvailable

            for (recipeFromApi in recipesFromApi) {
                finalRecipeList.add(recipeFromApi)
                for(recipeFromFavorite in recipesFromFavorites) {
                    if(recipeFromApi.recipeId == recipeFromFavorite.recipeId) {
                        recipeFromApi.isFavorite = true
                    }
                }
            }

            val searchState = SearchState(
                recipeList = finalRecipeList.toMutableList(),
                moreResultAvailable = moreResultsAvailable
            )

            emit(DataState.data(
                response = null,
                data = searchState
            ))

        } catch (e: Exception) {
            emit(
                DataState.error<SearchState>(
                    response = Response(
                        message = "SearchRecipes: ApiCall/CacheCall was Unsuccessful",
                        uiComponentType = UIComponentType.None,
                        messageType = MessageType.Error
                    )
                )
            )
        }
    }

}