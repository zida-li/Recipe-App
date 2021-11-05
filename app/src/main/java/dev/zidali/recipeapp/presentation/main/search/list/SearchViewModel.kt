package dev.zidali.recipeapp.presentation.main.search.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import dev.zidali.recipeapp.business.domain.models.Recipe
import dev.zidali.recipeapp.business.domain.util.*
import dev.zidali.recipeapp.business.interactors.main.apikey.FetchApiKey
import dev.zidali.recipeapp.business.interactors.main.search.SaveRecipeToFavorite
import dev.zidali.recipeapp.business.interactors.main.search.list.CompareSearchToFavorite
import dev.zidali.recipeapp.business.interactors.main.search.list.SaveRecipeToTemporaryRecipeDb
import dev.zidali.recipeapp.business.interactors.main.search.list.SearchRecipes
import dev.zidali.recipeapp.business.interactors.main.shared.DeleteRecipeFromFavorite
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
    private val fetchApiKey: FetchApiKey,
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
            is SearchEvents.CheckForApiKey -> {
                checkForApiKey()
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
                app_key = state.apiKey!!.app_key,
                app_id = state.apiKey!!.app_id,
                query = state.query,
                from = state.fromPage?: 0,
                to = state.toPage?: 10,
            ).onEach { dataState ->

                this.state.value = state.copy(isLoading = dataState.isLoading)

                dataState.data?.let { list->
                    this.state.value = state.copy(recipeList = list.recipeList)
                }

                dataState.stateMessage?.let { stateMessage ->
                    appendToMessageQueue(stateMessage)
                }

            }.launchIn(viewModelScope)
        }
    }

    private fun nextPage() {
        incrementPageNumber()
        state.value?.let { state->
            searchRecipes.execute(
                app_key = state.apiKey!!.app_key,
                app_id = state.apiKey!!.app_id,
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
//                Log.d(TAG, "onRemoveHeadFromQueue: Nothing to remove from DialogQueue")
            }
        }
    }

    /**
     * ApiKey
     */

    private fun checkForApiKey() {
        state.value?.let { state->
            fetchApiKey.execute().onEach { dataState ->

                setDataLoadingTrue()
                dataState.data?.let {
                    this.state.value = state.copy(apiKey = it)
                }
                setDataLoadingFalse()
                if(state.apiKey == null) {
                    if(!state.dataLoading) {
                        apiKeyNull()
                    }
                }

            }.launchIn(viewModelScope)
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

    private fun setDataLoadingTrue() {
        state.value?.dataLoading = true
    }

    private fun setDataLoadingFalse() {
        state.value?.dataLoading = false
    }

    private fun setNavigateToApiFragmentTrue () {
        state.value?.navigateToApiFragment = true

    }

    fun setNavigateToApiFragmentFalse () {
        state.value?.navigateToApiFragment = false
    }

    private fun apiKeyNull() {
        val callback: AreYouSureCallback = object: AreYouSureCallback {

            override fun proceed() {
                setNavigateToApiFragmentTrue()
            }

            override fun cancel() {
                //do nothing
            }
        }
        appendToMessageQueue(stateMessage = StateMessage(
            response = Response(
                message = "No ApiKey Found, Would you like to enter one?",
                uiComponentType = UIComponentType.AreYouSureDialog(callback),
                messageType = MessageType.Info
                )
            )
        )

    }

}