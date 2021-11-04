package us.zidali.recipeapp.presentation.main.search.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import us.zidali.recipeapp.business.domain.models.Recipe
import us.zidali.recipeapp.business.interactors.main.search.SaveRecipeToFavorite
import us.zidali.recipeapp.business.interactors.main.search.detail.FetchSearchRecipe
import us.zidali.recipeapp.business.interactors.main.shared.AddToShoppingList
import us.zidali.recipeapp.business.interactors.main.shared.CompareToShoppingList
import us.zidali.recipeapp.business.interactors.main.shared.DeleteRecipeFromFavorite
import us.zidali.recipeapp.business.interactors.main.shared.DeleteRecipeFromShoppingList
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
    private val compareToShoppingList: CompareToShoppingList,
    private val addToShoppingList: AddToShoppingList,
    private val deleteRecipeFromShoppingList: DeleteRecipeFromShoppingList,
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
            is ViewRecipeEvents.CompareSearchToShoppingList -> {
                compareSearchToShoppingList()
            }
            is ViewRecipeEvents.DeleteRecipeFromShoppingList -> {
                deleteRecipeFromShoppingList()
            }
            is ViewRecipeEvents.AddToShoppingList -> {
                addToShoppingList()
                saveRecipe()
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

                if(it?.recipeIngredients != null) {
                    for (ingredient in it.recipeIngredients!!) {
                        it.recipeIngredientCheck!!.add(
                            Recipe.Ingredient(
                                it.recipeName!!,
                                ingredient
                            )
                        )
                    }
                }

                state.value = state.value?.copy(recipe = it)
                compareSearchToShoppingList()
            }

        }.launchIn(viewModelScope)
    }

    private fun compareSearchToShoppingList() {
        compareToShoppingList.execute(
            state.value?.recipe!!
        ).onEach { dataState ->

            dataState.data.let {
                val ingredient = it?.recipeIngredients
                it?.recipeIngredients = null
                state.value = state.value?.copy(recipe = it)
                it?.recipeIngredients = ingredient
            }

        }.launchIn(viewModelScope)
    }

    private fun deleteRecipeFromShoppingList() {
        deleteRecipeFromShoppingList.execute(
            state.value?.recipe!!
        ).launchIn(viewModelScope)
    }

    private fun addToShoppingList() {
        addToShoppingList.execute(
            state.value?.recipe!!
        ).launchIn(viewModelScope)
    }

}