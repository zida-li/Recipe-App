package com.example.recipeappfivelearning.business.interactors.main.shared

import com.example.recipeappfivelearning.business.datasource.cache.main.FavoriteRecipeDao
import com.example.recipeappfivelearning.business.datasource.cache.main.shoppinglist.ShoppingListDao
import com.example.recipeappfivelearning.business.datasource.cache.main.shoppinglist.toShoppingListEntity
import com.example.recipeappfivelearning.business.datasource.cache.main.toFavoriteEntity
import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.business.domain.util.DataState
import com.example.recipeappfivelearning.business.domain.util.MessageType
import com.example.recipeappfivelearning.business.domain.util.Response
import com.example.recipeappfivelearning.business.domain.util.UIComponentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteRecipeFromShoppingList (
    private val shoppingListDao: ShoppingListDao,
) {

    fun execute(
        recipe: Recipe
    ): Flow<DataState<Recipe>> = flow {

        try {

            shoppingListDao.deleteRecipe(recipe.toShoppingListEntity())

        } catch (e: Exception) {
            emit(
                DataState.error<Recipe>(
                    response = Response(
                        message = "DeleteMultipleRecipesFromShoppingList: Deleting Recipes Was Unsuccessful",
                        uiComponentType = UIComponentType.None,
                        messageType = MessageType.Error
                    )
                )
            )
        }

    }

}