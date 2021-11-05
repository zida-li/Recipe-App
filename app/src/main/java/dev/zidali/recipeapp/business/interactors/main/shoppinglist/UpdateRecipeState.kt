package dev.zidali.recipeapp.business.interactors.main.shoppinglist

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListDao
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.toShoppingListEntity
import dev.zidali.recipeapp.business.domain.models.Recipe
import dev.zidali.recipeapp.business.domain.util.DataState
import dev.zidali.recipeapp.business.domain.util.MessageType
import dev.zidali.recipeapp.business.domain.util.Response
import dev.zidali.recipeapp.business.domain.util.UIComponentType
import java.lang.Exception

class UpdateRecipeState (
    private val shoppingListDao: ShoppingListDao
) {

    fun execute(
        recipe: Recipe
    ): Flow<DataState<Recipe>> = flow {

        try {
            shoppingListDao.insertRecipe(recipe.toShoppingListEntity())
        } catch (e: Exception) {
            emit(
                DataState.error<Recipe>(
                    response = Response(
                        message = "SetIsExpandedRecipe: Saving Is Unsuccessful",
                        uiComponentType = UIComponentType.None,
                        messageType = MessageType.Error
                    )
                )
            )
        }
    }

}