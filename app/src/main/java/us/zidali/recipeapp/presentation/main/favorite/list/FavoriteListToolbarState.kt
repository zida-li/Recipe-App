package us.zidali.recipeapp.presentation.main.favorite.list

sealed class FavoriteListToolbarState {

    object MultiSelectionState: FavoriteListToolbarState()

    object SearchState: FavoriteListToolbarState()

}