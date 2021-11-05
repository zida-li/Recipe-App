package dev.zidali.recipeapp.presentation.main.shoppinglist

import dev.zidali.recipeapp.business.domain.models.Recipe

sealed class ShoppingListEvents {

    object FetchShoppingList: ShoppingListEvents()

    /**
     * To save states for ExpandableGroups, on navigating back & on app reload
     */

    data class SetIsExpandedRecipe(
        val isExpanded: Boolean,
        val recipe: Recipe,
    ): ShoppingListEvents()

    data class SetIsCheckedIngredient(
        val ingredient: Recipe.Ingredient
    ): ShoppingListEvents()

    /**
     *MultiSelectionMode
     */

    object ActivateMultiSelectionMode: ShoppingListEvents()

    object DisableMultiSelectMode: ShoppingListEvents()

    data class AddOrRemoveRecipeFromSelectedList(
        val position: Int,
        val recipe: Recipe
    ): ShoppingListEvents()

    object DeleteSelectedRecipes: ShoppingListEvents()

}