package dev.zidali.recipeapp.presentation.main.shoppinglist

sealed class ShoppingListToolbarState {

    object MultiSelectionState: ShoppingListToolbarState()

    object SearchState: ShoppingListToolbarState()

}