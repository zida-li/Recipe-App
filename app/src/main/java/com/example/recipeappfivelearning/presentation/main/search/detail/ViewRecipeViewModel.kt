package com.example.recipeappfivelearning.presentation.main.search.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeappfivelearning.business.interactors.main.DeleteRecipeFromFavorite
import com.example.recipeappfivelearning.business.interactors.main.search.SaveRecipeToFavorite
import com.example.recipeappfivelearning.business.interactors.main.search.detail.FetchSearchRecipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ViewRecipeViewModel
@Inject
constructor(
    private val fetchSearchRecipe: FetchSearchRecipe,
    private val savedStateHandle: SavedStateHandle,
    private val saveRecipeToFavorite: SaveRecipeToFavorite,
    private val deleteRecipeFromFavorite: DeleteRecipeFromFavorite,
): ViewModel(){

    init {
        savedStateHandle.get<String>("recipeName")?.let {recipeName ->
            onTriggerEvent(ViewRecipeEvents.FetchSearchRecipe(recipeName))
        }
    }

    val state: MutableLiveData<ViewRecipeState> = MutableLiveData(ViewRecipeState())

    fun onTriggerEvent(event: ViewRecipeEvents) {
        when(event) {
            is ViewRecipeEvents.FetchSearchRecipe -> {
                fetchSearchRecipe(event.recipeName)
            }
            is ViewRecipeEvents.SaveRecipe -> {
                saveRecipe()
            }
            is ViewRecipeEvents.DeleteRecipe -> {
                deleteRecipe()
            }
        }
    }

    private fun saveRecipe() {
        state.value?.let { state->
            state.recipe = state.recipe?.copy(isFavorite = true)
            state.recipe?.let { recipe->
                val timeInserted = Calendar.getInstance().time.toString()
                recipe.timeSaved = timeInserted
                saveRecipeToFavorite.execute(
                    recipe
                ).launchIn(viewModelScope)
            }
        }
    }

    private fun deleteRecipe() {
        state.value?.let { state->
            state.recipe = state.recipe?.copy(isFavorite = false)
            state.recipe?.let { recipe->
                deleteRecipeFromFavorite.execute(
                    recipe
                ).launchIn(viewModelScope)
            }
        }
    }

    private fun fetchSearchRecipe(recipeName: String) {
        fetchSearchRecipe.execute(
            recipeName = recipeName
        ).onEach { dataState ->

            dataState.data?.recipe.let {
                state.value = state.value?.copy(recipe = it)
            }

        }.launchIn(viewModelScope)
    }

}