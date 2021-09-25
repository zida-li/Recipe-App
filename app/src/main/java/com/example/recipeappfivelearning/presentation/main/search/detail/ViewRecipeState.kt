package com.example.recipeappfivelearning.presentation.main.search.detail

import com.example.recipeappfivelearning.business.domain.util.Queue
import com.example.recipeappfivelearning.business.domain.util.StateMessage

data class ViewRecipeState (
    var recipe: ViewRecipeStateRecipeModel? = null,
    val isLoading: Boolean = false,
    val queue: Queue<StateMessage> = Queue(mutableListOf()),
) {

    data class ViewRecipeStateRecipeModel(
        var recipeName: String? = null,
        var recipeImageUrl: String? = null,
        var recipeIngredients: List<String>? = null,
        val recipeId: String? = null,
        var recipeCalories: Float? = null,
        var timeSaved: String? = null,
        var isFavorite: Boolean = false,
    )

}