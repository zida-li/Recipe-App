package com.example.recipeappfivelearning.business.domain.models

data class Ingredient (
    var recipeName: String? = null,
    var recipeIngredient: String? = null,
    var isChecked: Boolean = false
)