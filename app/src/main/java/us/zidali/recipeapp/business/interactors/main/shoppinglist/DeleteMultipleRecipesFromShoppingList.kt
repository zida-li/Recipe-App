package us.zidali.recipeapp.business.interactors.main.shoppinglist

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import us.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListDao
import us.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListIngredientDao
import us.zidali.recipeapp.business.datasource.cache.main.shoppinglist.toShoppingListEntity
import us.zidali.recipeapp.business.datasource.cache.main.shoppinglist.toShoppingListIngredientEntity
import us.zidali.recipeapp.business.domain.models.Recipe
import us.zidali.recipeapp.business.domain.util.DataState
import us.zidali.recipeapp.business.domain.util.MessageType
import us.zidali.recipeapp.business.domain.util.Response
import us.zidali.recipeapp.business.domain.util.UIComponentType

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