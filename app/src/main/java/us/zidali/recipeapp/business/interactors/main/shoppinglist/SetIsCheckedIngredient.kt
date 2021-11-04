package us.zidali.recipeapp.business.interactors.main.shoppinglist

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import us.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListIngredientDao
import us.zidali.recipeapp.business.datasource.cache.main.shoppinglist.toShoppingListIngredientEntity
import us.zidali.recipeapp.business.domain.models.Recipe
import us.zidali.recipeapp.business.domain.util.DataState
import us.zidali.recipeapp.business.domain.util.MessageType
import us.zidali.recipeapp.business.domain.util.Response
import us.zidali.recipeapp.business.domain.util.UIComponentType
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