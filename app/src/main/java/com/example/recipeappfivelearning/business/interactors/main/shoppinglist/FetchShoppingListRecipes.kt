package com.example.recipeappfivelearning.business.interactors.main.shoppinglist

import com.example.recipeappfivelearning.business.datasource.cache.main.shoppinglist.ShoppingListDao
import com.example.recipeappfivelearning.business.datasource.cache.main.shoppinglist.toShoppingListRecipe
import com.example.recipeappfivelearning.business.datasource.network.handleUseCaseException
import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.business.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class FetchShoppingListRecipes (
    private val shoppingListDao: ShoppingListDao,
) {

    fun execute(): Flow<DataState<MutableList<Recipe>>> = flow {

        val cacheCall = shoppingListDao.getAllRecipes().map {
            it.toShoppingListRecipe()
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