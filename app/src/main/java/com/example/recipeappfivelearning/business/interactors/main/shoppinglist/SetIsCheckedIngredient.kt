package com.example.recipeappfivelearning.business.interactors.main.shoppinglist

import com.example.recipeappfivelearning.business.datasource.cache.main.shoppinglist.ShoppingListIngredientDao
import com.example.recipeappfivelearning.business.datasource.cache.main.shoppinglist.toShoppingListIngredientEntity
import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.business.domain.util.DataState
import com.example.recipeappfivelearning.business.domain.util.MessageType
import com.example.recipeappfivelearning.business.domain.util.Response
import com.example.recipeappfivelearning.business.domain.util.UIComponentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class SetIsCheckedIngredient (
    private val shoppingListIngredientDao: ShoppingListIngredientDao
) {

    fun execute(
        ingredient: Recipe.Ingredient
    ): Flow<DataState<Recipe>> = flow {

        try {
            shoppingListIngredientDao.insertIngredient(ingredient.toShoppingListIngredientEntity())
        } catch (e: Exception) {
            emit(
                DataState.error<Recipe>(
                    response = Response(
                        message = "SetIsCheckedIngredient: Saving Is Unsuccessful",
                        uiComponentType = UIComponentType.None,
                        messageType = MessageType.Error
                    )
                )
            )
        }
    }

}