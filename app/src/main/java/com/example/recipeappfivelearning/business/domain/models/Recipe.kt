package com.example.recipeappfivelearning.business.domain.models

data class Recipe (
    var recipeName: String? = null,
    var recipeImageUrl: String? = null,
    var recipeIngredients: List<String>? = null,
    val recipeId: String? = null,
    var recipeCalories: Float? = null,
    var timeSaved: String? = null,
    var isFavorite: Boolean = false,
)