package com.example.recipeappfivelearning.presentation.main.shoppinglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.business.interactors.main.shoppinglist.DeleteMultipleRecipesFromShoppingList
import com.example.recipeappfivelearning.business.interactors.main.shoppinglist.FetchShoppingListRecipes
import com.example.recipeappfivelearning.business.interactors.main.shoppinglist.SetIsCheckedIngredient
import com.example.recipeappfivelearning.business.interactors.main.shoppinglist.UpdateRecipeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel
@Inject
constructor(
    private val fetchShoppingListRecipes: FetchShoppingListRecipes,
    private val deleteMultipleRecipesFromShoppingList: DeleteMultipleRecipesFromShoppingList,
    private val updateRecipeState: UpdateRecipeState,
    private val setIsCheckedIngredient: SetIsCheckedIngredient,
): ViewModel(){

    val state: MutableLiveData<ShoppingListState> = MutableLiveData(ShoppingListState())

    val toolbarState: LiveData<ShoppingListToolbarState>
        get() = shoppingListInteractionManager.toolbarState

    val shoppingListInteractionManager = ShoppingListInteractionManager()

    fun onTriggerEvent(event: ShoppingListEvents) {
        when(event) {
            is ShoppingListEvents.FetchShoppingList -> {
                fetchShoppingList()
            }
            is ShoppingListEvents.SetToolbarState -> {
                setToolbarState(event.shoppingListToolbarState)
            }
            is ShoppingListEvents.AddOrRemoveRecipeFromSelectedList -> {
                addOrRemoveRecipeFromSelectedList(event.recipe)
            }
            is ShoppingListEvents.AddOrRemoveRecipePositionFromSelectedList -> {
                addOrRemoveRecipePositionFromSelectedList(event.position)
            }
            is ShoppingListEvents.DeleteSelectedRecipes -> {
                deleteSelectedRecipes()
            }
            is ShoppingListEvents.ClearSelectedRecipes -> {
                clearSelectedRecipes()
            }
            is ShoppingListEvents.ClearSelectedRecipesPosition -> {
                removeSelectedRecipePositionsFromList()
            }
            is ShoppingListEvents.SetIsExpandedRecipe -> {
                setIsExpandedRecipe(event.isExpanded, event.recipe)
            }
            is ShoppingListEvents.SetIsCheckedIngredient -> {
                setIsCheckedIngredient(event.ingredient)
            }
            is ShoppingListEvents.ForceReloadForMultiSelectionMode -> {
                forceReloadForMultiSelectionMode()
            }
        }
    }

    private fun fetchShoppingList() {

        state.value?.let {state->

            fetchShoppingListRecipes.execute().onEach {dataState ->

                dataState.data?.let { list->

                    val initialSize = list.size

                    if(state.initialListSize != initialSize) {
                        state.initialListSize = list.size

                        setNeedToReloadToTrue()
                        this.state.value = state.copy(recipeList = list)
                        setNeedToReloadToFalse()

                    } else if (state.initialListSize == initialSize) {
                        setNeedToReloadToFalse()
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun forceReloadForMultiSelectionMode() {
        //Workaround because I cannot get updateAsync to work with ExpandableItems
        state.value?.let {state->
            fetchShoppingListRecipes.execute().onEach {dataState ->
                dataState.data?.let { list->
                    setNeedToReloadToTrue()
                    this.state.value = state.copy(recipeList = list)
                    setNeedToReloadToFalse()
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun setNeedToReloadToTrue() {
        state.value?.needToReload = true
    }

    private fun setNeedToReloadToFalse() {
        state.value?.needToReload = false
    }

    private fun setToolbarState(shoppingListToolbarState: ShoppingListToolbarState) {
        shoppingListInteractionManager.setToolbarState(shoppingListToolbarState)
    }

    private fun addOrRemoveRecipeFromSelectedList(recipe: Recipe) {
        shoppingListInteractionManager.addOrRemoveRecipeFromSelectedList(recipe)
    }

    private fun addOrRemoveRecipePositionFromSelectedList(position: Int) {
        shoppingListInteractionManager.addOrRemoveRecipePositionFromSelectedList(position)
    }

    private fun getSelectedRecipes() = shoppingListInteractionManager.getSelectedRecipes()

    fun getSelectedRecipesPosition() = shoppingListInteractionManager.getSelectedRecipesPosition()

    private fun deleteSelectedRecipes() {
        if(getSelectedRecipes().size > 0) {
            deleteMultipleRecipesFromShoppingList.execute(getSelectedRecipes()).launchIn(viewModelScope)
            removeSelectedRecipesFromList()
            removeSelectedRecipePositionsFromList()
        }
    }

    private fun removeSelectedRecipesFromList() {
        state.value?.recipeList?.removeAll(getSelectedRecipes())
        clearSelectedRecipes()
    }

    private fun removeSelectedRecipePositionsFromList() {
        shoppingListInteractionManager.clearSelectedRecipesPosition()
    }

    private fun clearSelectedRecipes() {
        shoppingListInteractionManager.clearSelectedRecipes()
    }

    private fun setIsExpandedRecipe(isExpanded: Boolean, recipe: Recipe) {
        state.value?.let { state->
            for(mRecipe in state.recipeList) {
                if(mRecipe.recipeName == recipe.recipeName){
                    mRecipe.isExpanded = isExpanded
                    updateRecipeState.execute(mRecipe).launchIn(viewModelScope)
                }
            }
        }
    }

    private fun setIsCheckedIngredient(item: Recipe.Ingredient) {
        state.value?.let { state->
            for(recipe in state.recipeList) {
                for(ingredient in recipe.recipeIngredientCheck!!) {
                    if(ingredient.recipeIngredient == item.recipeIngredient) {
                        item.isChecked = !item.isChecked
                        setIsCheckedIngredient.execute(item).launchIn(viewModelScope)
                    }
                }
            }
        }
    }

    fun setMultiSelectionModeToTrue() {
        for(recipe in state.value?.recipeList!!) {
            recipe.isMultiSelectionModeEnabled = true
            updateRecipeState.execute(recipe).launchIn(viewModelScope)
        }
    }

    fun setMultiSelectionModeToFalse() {
        for(recipe in state.value?.recipeList!!) {
            recipe.isMultiSelectionModeEnabled = false
            updateRecipeState.execute(recipe).launchIn(viewModelScope)
        }
    }

}