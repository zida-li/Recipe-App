package dev.zidali.recipeapp.datasource.network.main.search

import dev.zidali.recipeapp.business.domain.models.Recipe
import dev.zidali.recipeapp.business.domain.util.Converters

object SingleDuplicateRecipe {

    val recipeName = "Chicken Vesuvio"
    val recipeImageUrl = "http://www.seriouseats.com/recipes/2011/12/chicken-vesuvio-recipe.html"
    val recipeIngredients = null
    val recipeId = "http://www.edamam.com/ontologies/edamam.owl#recipe_b79327d05b8e5b838ad6cfd9576b30b6"
    val recipeCalories = 4228.043058200812.toFloat()
    val timeSaved = null
    val isExpanded = false
    val isFavorite = false
    val isInShoppingList = false


    val recipe = Recipe(
        recipeName = recipeName,
        recipeImageUrl = recipeImageUrl,
        recipeIngredients = Converters.convertIngredientsToList(recipeIngredients),
        recipeId = recipeId,
        recipeCalories = recipeCalories,
        timeSaved = timeSaved,
        isExpanded = isExpanded,
        isFavorite = isFavorite,
        isInShoppingList = isInShoppingList,
    )


}