package com.example.recipeappfivelearning.presentation.main.search.list

import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.business.domain.util.Queue
import com.example.recipeappfivelearning.business.domain.util.StateMessage

data class SearchState (
    var recipeList: MutableList<Recipe> = mutableListOf(),
    var recipe: Recipe? = null,
    var query: String = "",
    val isLoading: Boolean = false,
    var moreResultAvailable: Boolean? = null,
    var page: Int? = null,
    var fromPage: Int? = null,
    var toPage: Int? = null,
    val queue: Queue<StateMessage> = Queue(mutableListOf()),
)