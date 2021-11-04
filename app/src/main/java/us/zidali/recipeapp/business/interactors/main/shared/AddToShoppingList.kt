package us.zidali.recipeapp.business.interactors.main.shared

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