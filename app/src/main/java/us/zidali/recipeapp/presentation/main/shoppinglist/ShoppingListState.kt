package us.zidali.recipeapp.presentation.main.shoppinglist

import us.zidali.recipeapp.business.domain.models.Recipe
import us.zidali.recipeapp.business.domain.util.Queue
import us.zidali.recipeapp.business.domain.util.StateMessage

data class ShoppingListState (

    var recipeList: MutableList<Recipe> = mutableListOf(),
    var recipe: Recipe? = null,
    var query: String = "",
    val isLoading: Boolean = false,
    var initialListSize: Int? = null,
    var needToReload: Boolean = false,
    var scrollPosition: Int = 0,
    val queue: Queue<StateMessage> = Queue(mutableListOf()),

    )