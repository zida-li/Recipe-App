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

    data class SetIsCheckedIngredient(
        val ingredient: Recipe.Ingredient
    ): ShoppingListEvents()

    data class SetIsExpandedRecipe(
        val recipe: Recipe
    ): ShoppingListEvents()

}