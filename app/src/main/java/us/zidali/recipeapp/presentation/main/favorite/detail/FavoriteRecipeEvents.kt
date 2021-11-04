package us.zidali.recipeapp.presentation.main.favorite.detail

sealed class FavoriteRecipeEvents{

    data class FetchFavoriteRecipe(
        val recipeName: String
    ): FavoriteRecipeEvents()

    object DeleteRecipe: FavoriteRecipeEvents()

    object AddToShoppingList: FavoriteRecipeEvents()

    object CompareFavoriteToShoppingList: FavoriteRecipeEvents()

    object DeleteRecipeFromShoppingList: FavoriteRecipeEvents()

}