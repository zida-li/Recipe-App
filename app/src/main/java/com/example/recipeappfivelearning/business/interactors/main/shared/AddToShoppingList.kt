package com.example.recipeappfivelearning.business.interactors.main.shared

import android.util.Log
import com.example.recipeappfivelearning.business.datasource.cache.main.shoppinglist.ShoppingListDao
import com.example.recipeappfivelearning.business.datasource.cache.main.shoppinglist.ShoppingListIngredientDao
import com.example.recipeappfivelearning.business.datasource.cache.main.shoppinglist.toShoppingListEntity
import com.example.recipeappfivelearning.business.datasource.cache.main.shoppinglist.toShoppingListIngredientEntity
import com.example.recipeappfivelearning.business.datasource.cache.main.toFavoriteEntity
import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.business.domain.util.DataState
import com.example.recipeappfivelearning.business.domain.util.MessageType
import com.example.recipeappfivelearning.business.domain.util.Response
import com.example.recipeappfivelearning.business.domain.util.UIComponentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class AddToShoppingList (
    private val shoppingListDao: ShoppingListDao,
    private val shoppingListIngredientDao: ShoppingListIngredientDao,
) {

    fun execute(
        recipe: Recipe
    ): Flow<DataState<Recipe>> = flow {

        try {

            shoppingListDao.insertRecipe(recipe.toShoppingListEntity())

            for (ingredient in recipe.recipeIngredients!!) {
                recipe.recipeIngredient = ingredient
                if(recipe.recipeIngredient!= "") {
                    shoppingListIngredientDao.insertIngredient(
                        recipe = recipe.toShoppingListIngredientEntity()
                    )
                }
            }
        } catch (e: Exception) {
            emit(
                DataState.error<Recipe>(
                    response = Response(
                        message = "AddToShoppingList: Saving Recipe Is Unsuccessful",
                        uiComponentType = UIComponentType.None,
                        messageType = MessageType.Error
                    )
                )
            )
        }

    }

}