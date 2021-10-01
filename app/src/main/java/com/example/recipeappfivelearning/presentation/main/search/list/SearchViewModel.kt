package com.example.recipeappfivelearning.presentation.main.search.list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.business.domain.util.Constants
import com.example.recipeappfivelearning.business.domain.util.StateMessage
import com.example.recipeappfivelearning.business.domain.util.UIComponentType
import com.example.recipeappfivelearning.business.domain.util.doesMessageAlreadyExistInQueue
import com.example.recipeappfivelearning.business.interactors.main.DeleteRecipeFromFavorite
import com.example.recipeappfivelearning.business.interactors.main.search.SaveRecipeToFavorite
import com.example.recipeappfivelearning.business.interactors.main.search.list.CompareSearchToFavorite
import com.example.recipeappfivelearning.business.interactors.main.search.list.SaveRecipeToTemporaryRecipeDb
import com.example.recipeappfivelearning.business.interactors.main.search.list.SearchRecipes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.lang.Exception
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
@Inject
constructor(
    private val searchRecipes: SearchRecipes,
    private val saveRecipeToTemporaryRecipeDb: SaveRecipeToTemporaryRecipeDb,
    private val saveRecipeToFavorite: SaveRecipeToFavorite,
    private val deleteRecipeFromFavorite: DeleteRecipeFromFavorite,
    private val compareSearchToFavorite: CompareSearchToFavorite,
): ViewModel() {

    private val TAG: String = "AppDebug"

    val state: MutableLiveData<SearchState> = MutableLiveData(SearchState())

    fun onTriggerEvent(event: SearchEvents) {
        when(event) {
            is SearchEvents.NewSearch -> {
                newSearch()
            }
            is SearchEvents.UpdateQuery -> {
                onUpdateQuery(event.query)
            }
            is SearchEvents.NextPage -> {
                nextPage()
            }
            is SearchEvents.SaveRecipe -> {
                saveRecipe(event.recipe)
            }
            is SearchEvents.DeleteRecipe -> {
                deleteRecipe(event.recipe)
            }
            is SearchEvents.SaveToTemporaryRecipeDb -> {
                saveRecipeToTemporaryDb(event.recipe)
            }
            is SearchEvents.SaveOrDeleteRecipeFromDb -> {
                saveOrDeleteRecipeFromDb(event.recipe)
            }
            is SearchEvents.CompareSearchToFavorite -> {
                compareSearchToFavorite()
            }
            is SearchEvents.OnRemoveHeadFromQueue -> {
                onRemoveHeadFromQueue()
            }
            is SearchEvents.Error -> {
                appendToMessageQueue(event.stateMessage)
            }
        }
    }

    private fun newSearch() {
        clearList()
        state.value?.let { state->
            searchRecipes.execute(
                app_key = Constants.app_key,
                app_id = Constants.app_id,
                query = state.query,
                from = 0,
                to = 10
            ).onEach { dataState ->

                this.state.value = state.copy(isLoading = dataState.isLoading)

                dataState.data?.let { searchState->
                    this.state.value = state.copy(recipeList = searchState.recipeList)
                }

            }.launchIn(viewModelScope)
        }
    }

    private fun onUpdateQuery(query: String) {
        state.value = state.value?.copy(query = query)
    }

    private fun nextPage() {
        TODO("Not yet implemented")
    }

    private fun saveRecipe(recipe: Recipe) {
        setRecipeSavedTime(recipe)
        saveRecipeToFavorite.execute(recipe).launchIn(viewModelScope)
    }

    private fun deleteRecipe(recipe: Recipe) {
        deleteRecipeFromFavorite.execute(recipe).launchIn(viewModelScope)
    }

    private fun saveRecipeToTemporaryDb(recipe: Recipe) {
        saveRecipeToTemporaryRecipeDb.execute(
            recipe = recipe
        ).launchIn(viewModelScope)
    }

    private fun saveOrDeleteRecipeFromDb(recipe: Recipe) {
        if(recipe.isFavorite) {
            recipe.isFavorite = false
            onTriggerEvent(SearchEvents.DeleteRecipe(recipe))
        } else {
            recipe.isFavorite = true
            setRecipeSavedTime(recipe)
            onTriggerEvent(SearchEvents.SaveRecipe(recipe))
        }
    }

    private fun compareSearchToFavorite() {
        compareSearchToFavorite.execute(
            state.value!!.recipeList
        ).launchIn(viewModelScope)
    }

    private fun onRemoveHeadFromQueue() {
        state.value?.let { state->
            try {
                val queue = state.queue
                queue.remove()
                this.state.value = state.copy(queue = queue)
            } catch (e: Exception) {
                Log.d(TAG, "onRemoveHeadFromQueue: Nothing to remove from DialogQueue")
            }
        }
    }

    private fun appendToMessageQueue(stateMessage: StateMessage) {
        state.value?.let { state->
            val queue = state.queue
            if(!(stateMessage.doesMessageAlreadyExistInQueue(queue = queue))){
                if(stateMessage.response.uiComponentType !is UIComponentType.None){
                    queue.add(stateMessage)
                    this.state.value = state.copy(queue = queue)
                }
            }
        }
    }

    private fun setRecipeSavedTime(recipe: Recipe) {
        val timeInserted = Calendar.getInstance().time.toString()
        recipe.timeSaved = timeInserted
    }

    private fun clearList() {
        state.value?.let { state->
            this.state.value = state.copy(recipeList = mutableListOf())
        }
    }

}