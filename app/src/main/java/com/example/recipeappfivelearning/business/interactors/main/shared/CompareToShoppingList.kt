package com.example.recipeappfivelearning.business.interactors.main.shared

import com.example.recipeappfivelearning.business.datasource.cache.main.shoppinglist.ShoppingListDao
import com.example.recipeappfivelearning.business.datasource.cache.main.shoppinglist.toShoppingListRecipe
import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.business.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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