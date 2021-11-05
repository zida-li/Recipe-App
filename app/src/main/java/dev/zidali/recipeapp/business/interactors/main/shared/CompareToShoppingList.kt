package dev.zidali.recipeapp.business.interactors.main.shared

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListDao
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.toShoppingListRecipe
import dev.zidali.recipeapp.business.domain.models.Recipe
import dev.zidali.recipeapp.business.domain.util.DataState

class CompareToShoppingList (
    private val shoppingListDao: ShoppingListDao,
) {

    fun execute (
        recipe: Recipe
    ): Flow<DataState<Recipe>> = flow {

        val recipesFromShoppingList = shoppingListDao.getAllRecipes().map {
            it.toShoppingListRecipe()
        }

        recipe.isInShoppingList = false

        for(recipeFromShoppingList in recipesFromShoppingList) {
            if(recipe.recipeId == recipeFromShoppingList.recipeId) {
                recipe.isInShoppingList = true
            }
        }

        emit(DataState.data(
            response = null,
            data = recipe
        ))

    }

}