package us.zidali.recipeapp.business.interactors.main.search.detail

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import us.zidali.recipeapp.business.datasource.cache.main.search.TemporaryRecipeDao
import us.zidali.recipeapp.business.datasource.cache.main.search.toTemporaryRecipe
import us.zidali.recipeapp.business.datasource.network.handleUseCaseException
import us.zidali.recipeapp.business.domain.util.DataState
import us.zidali.recipeapp.presentation.main.search.detail.ViewRecipeState

class FetchSearchRecipe (
    private val temporaryRecipeDao: TemporaryRecipeDao
) {

    fun execute(
        recipeName: String
    ): Flow<DataState<ViewRecipeState>> = flow {

        val cacheCall = temporaryRecipeDao.getRecipeByLabel(recipeName).toTemporaryRecipe()

        temporaryRecipeDao.deleteAllRecipes()

        val viewState = ViewRecipeState(
            recipe = cacheCall
        )

        emit(
            DataState.data(
                response = null,
                data = viewState
            )
        )

    }.catch { e->
        emit(handleUseCaseException(e))
    }

}