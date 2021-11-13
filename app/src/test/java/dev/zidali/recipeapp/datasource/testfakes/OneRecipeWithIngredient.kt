package dev.zidali.recipeapp.datasource.testfakes

import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListEntity
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListIngredientEntity
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.relations.RecipeWithIngredient

object OneRecipeWithIngredient {

    val recipeName = "Chicken Vesuvio"

    val recipeWithIngredient = RecipeWithIngredient(
        recipe = ShoppingListEntity(
            recipeName = recipeName,
            recipeImageUrl = "http://www.seriouseats.com/recipes/2011/12/chicken-vesuvio-recipe.html",
            recipeIngredients = "",
            recipeId = "http://www.edamam.com/ontologies/edamam.owl#recipe_b79327d05b8e5b838ad6cfd9576b30b6",
            recipeCalories = 4228.043058200812.toFloat(),
            timeSaved = null,
            isExpanded = false,
            isInShoppingList = false,
            isMultiSelectionModeEnabled = false,
        ),
        ingredients = mutableListOf<ShoppingListIngredientEntity>(
            ShoppingListIngredientEntity(
                recipeIngredient = "1/2 cup olive oil",
                recipeName = recipeName,
                isChecked = false,
            ),
            ShoppingListIngredientEntity(
                recipeIngredient = "5 cloves garlic, peeled",
                recipeName = recipeName,
                isChecked = false,
            ),
            ShoppingListIngredientEntity(
                recipeIngredient = "2 large russet potatoes, peeled and cut into chunks",
                recipeName = recipeName,
                isChecked = false,
            ),
            ShoppingListIngredientEntity(
                recipeIngredient = "1 3-4 pound chicken, cut into 8 pieces (or 3 pound chicken legs)",
                recipeName = recipeName,
                isChecked = false,
            ),
            ShoppingListIngredientEntity(
                recipeIngredient = "3/4 cup white wine",
                recipeName = recipeName,
                isChecked = false,
            ),
            ShoppingListIngredientEntity(
                recipeIngredient = "3/4 cup chicken stock",
                recipeName = recipeName,
                isChecked = false,
            ),
            ShoppingListIngredientEntity(
                recipeIngredient = "3 tablespoons chopped parsley",
                recipeName = recipeName,
                isChecked = false,
            ),
            ShoppingListIngredientEntity(
                recipeIngredient = "1 tablespoon dried oregano",
                recipeName = recipeName,
                isChecked = false,
            ),
            ShoppingListIngredientEntity(
                recipeIngredient = "Salt and pepper",
                recipeName = recipeName,
                isChecked = false,
            ),
            ShoppingListIngredientEntity(
                recipeIngredient = "1 cup frozen peas, thawed",
                recipeName = recipeName,
                isChecked = false,
            ),
        ),
    )

}