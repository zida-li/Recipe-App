package dev.zidali.recipeapp.datasource.cache

import dev.zidali.recipeapp.business.datasource.cache.main.FavoriteRecipeEntity
import dev.zidali.recipeapp.business.datasource.cache.main.search.TemporaryRecipeEntity

class AppDatabaseFake {

    val favoriteRecipe = mutableListOf<FavoriteRecipeEntity>()
    val temporaryRecipe = mutableListOf<TemporaryRecipeEntity>()

}