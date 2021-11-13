package dev.zidali.recipeapp.datasource.cache

import dev.zidali.recipeapp.business.datasource.cache.main.FavoriteRecipeEntity
import dev.zidali.recipeapp.business.datasource.cache.main.search.TemporaryRecipeEntity
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListEntity
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListIngredientEntity
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.relations.RecipeWithIngredient

class AppDatabaseFake {

    val favoriteRecipe = mutableListOf<FavoriteRecipeEntity>()
    val temporaryRecipe = mutableListOf<TemporaryRecipeEntity>()
    val shoppingList = mutableListOf<ShoppingListEntity>()
    val shoppingListIngredient = mutableListOf<ShoppingListIngredientEntity>()
    val recipeWithIngredient = mutableListOf<RecipeWithIngredient>()

}