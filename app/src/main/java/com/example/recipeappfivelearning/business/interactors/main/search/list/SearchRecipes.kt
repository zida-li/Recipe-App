package com.example.recipeappfivelearning.business.interactors.main.search.list

import com.example.recipeappfivelearning.business.datasource.network.main.MainService
import com.example.recipeappfivelearning.business.datasource.network.main.toSearchStateRecipeModel
import com.example.recipeappfivelearning.business.domain.util.DataState
import com.example.recipeappfivelearning.business.domain.util.MessageType
import com.example.recipeappfivelearning.business.domain.util.Response
import com.example.recipeappfivelearning.business.domain.util.UIComponentType
import com.example.recipeappfivelearning.presentation.main.search.list.SearchState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchRecipes (
    private val mainService: MainService,
) {

    fun execute(
        app_id: String,
        app_key: String,
        query: String,
        from: Int,
        to: Int
    ): Flow<DataState<SearchState>> = flow {

        try {

            val apiCall = mainService.search(
                app_id,
                app_key,
                query,
                from,
                to).recipeHits.map { it.toSearchStateRecipeModel() }

            val searchState = SearchState(
                recipeList = apiCall.toMutableList()
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