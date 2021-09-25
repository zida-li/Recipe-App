package com.example.recipeappfivelearning.presentation.main.search.list

import com.example.recipeappfivelearning.business.domain.util.Queue
import com.example.recipeappfivelearning.business.domain.util.StateMessage

data class SearchState (
    var recipeList: MutableList<SearchStateRecipeModel> = mutableListOf(),
    var recipe: SearchStateRecipeModel? = null,
    var query: String = "",
    val isLoading: Boolean = false,
    var moreResultAvailable: Boolean? = null,
    var page: Int? = null,
    var fromPage: Int? = null,
    var toPage: Int? = null,
    val queue: Queue<StateMessage> = Queue(mutableListOf()),
) {

    data class SearchStateRecipeModel(
        var recipeName: String? = null,
        var recipeImageUrl: String? = null,
        var recipeIngredients: List<String>? = null,
        val recipeId: String? = null,
        var recipeCalories: Float? = null,
        var timeSaved: String? = null,
        var isFavorite: Boolean = false,
    )

}