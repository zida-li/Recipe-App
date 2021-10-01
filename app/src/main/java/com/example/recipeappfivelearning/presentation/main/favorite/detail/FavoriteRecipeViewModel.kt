package com.example.recipeappfivelearning.presentation.main.favorite.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeappfivelearning.business.interactors.main.DeleteRecipeFromFavorite
import com.example.recipeappfivelearning.business.interactors.main.favorite.detail.FetchFavoriteRecipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FavoriteRecipeViewModel
@Inject
constructor(
    private val fetchFavoriteRecipe: FetchFavoriteRecipe,
    private val savedStateHandle: SavedStateHandle,
    private val deleteRecipeFromFavorite: DeleteRecipeFromFavorite,
): ViewModel(){

    init {
        savedStateHandle.get<String>("favoriteRecipeName")?.let {recipeName ->
            onTriggerEvent(FavoriteRecipeEvents.FetchFavoriteRecipe(recipeName))
        }
    }

    val state: MutableLiveData<FavoriteRecipeState> = MutableLiveData(FavoriteRecipeState())

    fun onTriggerEvent(event: FavoriteRecipeEvents) {
        when(event) {
            is FavoriteRecipeEvents.FetchFavoriteRecipe -> {
                fetchFavoriteRecipe(event.recipeName)
            }
            is FavoriteRecipeEvents.DeleteRecipe -> {
                deleteRecipe()
            }
        }
    }

    private fun deleteRecipe() {
        state.value?.let { state->
            state.recipe?.let { recipe->
                deleteRecipeFromFavorite.execute(
                    recipe
                ).launchIn(viewModelScope)
            }
        }
    }

    private fun fetchFavoriteRecipe(recipeName: String) {
        fetchFavoriteRecipe.execute(
            recipeName = recipeName
        ).onEach { dataState ->

            dataState.data?.let {
                state.value = state.value?.copy(recipe = it)
            }

        }.launchIn(viewModelScope)
    }

}