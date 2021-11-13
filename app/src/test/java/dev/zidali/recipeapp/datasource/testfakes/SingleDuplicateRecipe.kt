package dev.zidali.recipeapp.datasource.testfakes

import dev.zidali.recipeapp.business.domain.models.Recipe

object SingleDuplicateRecipe {

    val recipeName = "Chicken Vesuvio"
    val recipeImageUrl = "http://www.seriouseats.com/recipes/2011/12/chicken-vesuvio-recipe.html"
    val recipeIngredients = listOf(
        "1/2 cup olive oil",
        "5 cloves garlic, peeled",
        "2 large russet potatoes, peeled and cut into chunks",
        "1 3-4 pound chicken, cut into 8 pieces (or 3 pound chicken legs)",
        "3/4 cup white wine",
        "3/4 cup chicken stock",
        "3 tablespoons chopped parsley",
        "1 tablespoon dried oregano",
        "Salt and pepper",
        "1 cup frozen peas, thawed"
    )
    val recipeId = "http://www.edamam.com/ontologies/edamam.owl#recipe_b79327d05b8e5b838ad6cfd9576b30b6"
    val recipeCalories = 4228.043058200812.toFloat()
    val timeSaved = null
    val isExpanded = false
    val isFavorite = false
    val isInShoppingList = false


    val recipe = Recipe(
        recipeName = recipeName,
        recipeImageUrl = recipeImageUrl,
        recipeIngredients = recipeIngredients,
        recipeId = recipeId,
        recipeCalories = recipeCalories,
        timeSaved = timeSaved,
        isExpanded = isExpanded,
        isFavorite = isFavorite,
        isInShoppingList = isInShoppingList,
    )


}