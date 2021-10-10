package com.example.recipeappfivelearning.presentation.main.shoppinglist

import com.example.recipeappfivelearning.business.domain.models.Recipe

sealed class ShoppingListEvents {

    object FetchShoppingList: ShoppingListEvents()

    data class SetToolbarState(
        val shoppingListToolbarState: ShoppingListToolbarState
    ): ShoppingListEvents()

    data class AddOrRemoveRecipeFromSelectedList(
        val recipe: Recipe
    ): ShoppingListEvents()

    data class AddOrRemoveRecipePositionFromSelectedList(
        val position: Int
    ): ShoppingListEvents()

    object DeleteSelectedRecipes: ShoppingListEvents()

    object ClearSelectedRecipes: ShoppingListEvents()

    object ClearSelectedRecipesPosition: ShoppingListEvents()

}