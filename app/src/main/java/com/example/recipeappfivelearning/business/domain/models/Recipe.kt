package com.example.recipeappfivelearning.business.domain.models

data class Recipe (
    var recipeName: String? = null,
    var recipeImageUrl: String? = null,
    var recipeIngredient: String? = null,
    var recipeIngredients: List<String>? = null,
    var recipeIngredientCheck: MutableList<Ingredient>? = mutableListOf(),
    var recipeIngredientCheckEmpty: MutableList<Ingredient> = mutableListOf(),
    val recipeId: String? = null,
    var recipeCalories: Float? = null,
    var timeSaved: String? = null,
    var isFavorite: Boolean = false,
    var isInShoppingList: Boolean = false,
    var isExpanded: Boolean = false,
    var isMultiSelectionModeEnabled: Boolean = false,
) {

    data class Ingredient(
        var recipeName: String,
        var recipeIngredient: String,
        var isChecked: Boolean = false
    )

}