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
import com.example.recipeappfivelearning.business.interactors.main.shared.DeleteRecipeFromFavorite
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
            is SearchEvents.NextPage -> {
                nextPage()
            }
            is SearchEvents.UpdateQuery -> {
                onUpdateQuery(event.query)
            }
            is SearchEvents.CompareSearchToFavorite -> {
                compareSearchToFavorite()
            }
            is SearchEvents.SaveOrDeleteRecipeFromDb -> {
                saveOrDeleteRecipeFromDb(event.recipe)
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
            is SearchEvents.AppendToMessageQueue -> {
                appendToMessageQueue(event.stateMessage)
            }
            is SearchEvents.OnRemoveHeadFromQueue -> {
                onRemoveHeadFromQueue()
            }
        }
    }

    /**
     * Search
     */

    private fun newSearch() {
        resetSearchState()
        state.value?.let { state->
            searchRecipes.execute(
                app_key = Constants.app_key,
                app_id = Constants.app_id,
                query = state.query,
                from = state.fromPage?: 0,
                to = state.toPage?: 10,
            ).onEach { dataState ->

                this.state.value = state.copy(isLoading = dataState.isLoading)

                dataState.data?.let { list->
                    this.state.value = state.copy(recipeList = list.recipeList)
                }

            }.launchIn(viewModelScope)
        }
    }

    private fun nextPage() {
        incrementPageNumber()
        state.value?.let { state->
            searchRecipes.execute(
                app_key = Constants.app_key,
                app_id = Constants.app_id,
                query = state.query,
                from = state.fromPage?: 0,
                to = state.toPage?: 10,
            ).onEach { dataState->

                this.state.value = state.copy(isLoading = dataState.isLoading)

                dataState.data?.let { list->
                    appendRecipes(list.recipeList)
                }

            }.launchIn(viewModelScope)
        }
    }

    private fun onUpdateQuery(query: String) {
        state.value = state.value?.copy(query = query)
    }

    private fun compareSearchToFavorite() {
        compareSearchToFavorite.execute(
            state.value!!.recipeList
        ).launchIn(viewModelScope)
    }

    /**
     * Save/Delete To Database
     */

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

    /**
     * Alert Dialogs
     */

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

    /**
     * Supporting Functions
     */

    private fun setRecipeSavedTime(recipe: Recipe) {
        val timeInserted = Calendar.getInstance().time.toString()
        recipe.timeSaved = timeInserted
    }

    private fun resetSearchState() {
        state.value?.let { state->
            this.state.value = state.copy(recipeList = mutableListOf())
            state.page = 0
            state.fromPage = 0
            state.toPage = 10
        }
    }

    private fun incrementPageNumber() {
        val page = state.value?.copy()!!.page?: 1
        val fromPage = state.value?.copy()!!.fromPage?: 0
        val toPage = state.value?.copy()!!.toPage?: 10
        state.value?.page = page.plus(1)
        state.value?.fromPage = fromPage.plus(11)
        state.value?.toPage = toPage.plus(11)
    }

    private fun appendRecipes(recipes: MutableList<Recipe>) {
        val current = state.value?.recipeList
        current?.addAll(recipes)
        state.value?.recipeList = current!!
    }

}