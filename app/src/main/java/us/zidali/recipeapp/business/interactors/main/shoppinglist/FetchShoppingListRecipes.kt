package us.zidali.recipeapp.business.interactors.main.shoppinglist

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import us.zidali.recipeapp.business.datasource.cache.main.shoppinglist.relations.RecipeWithIngredientDao
import us.zidali.recipeapp.business.datasource.cache.main.shoppinglist.relations.toRecipeWithIngredient
import us.zidali.recipeapp.business.datasource.network.handleUseCaseException
import us.zidali.recipeapp.business.domain.models.Recipe
import us.zidali.recipeapp.business.domain.util.DataState

class FetchShoppingListRecipes (
    private val recipeWithIngredientDao: RecipeWithIngredientDao
) {

    fun execute(): Flow<DataState<MutableList<Recipe>>> = flow {

        val cacheCall = recipeWithIngredientDao.getRecipeWithIngredient().map {
            it.toRecipeWithIngredient()
        }


        emit(
            DataState.data(
                response = null,
                data = cacheCall.toMutableList()
            )
        )

    }.catch { e->
        emit(handleUseCaseException(e))
    }

}