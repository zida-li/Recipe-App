package dev.zidali.recipeapp.datasource.testfakes

import dev.zidali.recipeapp.business.domain.models.Recipe

object OneIngredient {

    val ingredient = Recipe.Ingredient(
        recipeIngredient = "1/2 cup olive oil",
        recipeName = "Chicken Vesuvio",
        isChecked = false,
    )

}