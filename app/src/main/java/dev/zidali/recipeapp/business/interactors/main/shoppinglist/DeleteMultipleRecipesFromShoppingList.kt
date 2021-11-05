package dev.zidali.recipeapp.business.interactors.main.shoppinglist

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListDao
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListIngredientDao
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.toShoppingListEntity
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.toShoppingListIngredientEntity
import dev.zidali.recipeapp.business.domain.models.Recipe
import dev.zidali.recipeapp.business.domain.util.DataState
import dev.zidali.recipeapp.business.domain.util.MessageType
import dev.zidali.recipeapp.business.domain.util.Response
import dev.zidali.recipeapp.business.domain.util.UIComponentType

class DeleteMultipleRecipesFromShoppingList (
    private val shoppingListDao: ShoppingListDao,
    private val shoppingListIngredientDao: ShoppingListIngredientDao,
) {

    fun execute(
        recipes: List<Recipe>
    ): Flow<DataState<Recipe>> = flow {

        try {
            for (recipe in recipes) {
                shoppingListDao.deleteRecipe(recipe.toShoppingListEntity())
                for (ingredient in recipe.recipeIngredientCheck!!) {
                    shoppingListIngredientDao.deleteRecipe(ingredient.toShoppingListIngredientEntity())
                }
            }
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