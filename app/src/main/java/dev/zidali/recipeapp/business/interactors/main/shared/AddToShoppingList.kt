package dev.zidali.recipeapp.business.interactors.main.shared

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