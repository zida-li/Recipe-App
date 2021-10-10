package com.example.recipeappfivelearning.presentation.main.shoppinglist

sealed class ShoppingListToolbarState {

    object MultiSelectionState: ShoppingListToolbarState()

    object SearchState: ShoppingListToolbarState()

}