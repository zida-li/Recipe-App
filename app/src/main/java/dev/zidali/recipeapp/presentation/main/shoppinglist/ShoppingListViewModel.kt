package dev.zidali.recipeapp.presentation.main.shoppinglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import dev.zidali.recipeapp.business.domain.models.Recipe
import dev.zidali.recipeapp.business.interactors.main.shoppinglist.DeleteMultipleRecipesFromShoppingList
import dev.zidali.recipeapp.business.interactors.main.shoppinglist.FetchShoppingListRecipes
import dev.zidali.recipeapp.business.interactors.main.shoppinglist.SetIsCheckedIngredient
import dev.zidali.recipeapp.business.interactors.main.shoppinglist.UpdateRecipeState
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
            is ShoppingListEvents.SetIsExpandedRecipe -> {
                setIsExpandedRecipe(event.isExpanded, event.recipe)
            }
            is ShoppingListEvents.SetIsCheckedIngredient -> {
                setIsCheckedIngredient(event.ingredient)
            }
            is ShoppingListEvents.ActivateMultiSelectionMode -> {
                activateMultiSelectionMode()
            }
            is ShoppingListEvents.DisableMultiSelectMode -> {
                disableMultiSelectionMode()
            }
            is ShoppingListEvents.AddOrRemoveRecipeFromSelectedList -> {
                addOrRemoveRecipeFromSelectedList(event.position, event.recipe)
            }
            is ShoppingListEvents.DeleteSelectedRecipes -> {
                deleteSelectedRecipes()
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

                        setDataLoadingTrue()
                        this.state.value = state.copy(recipeList = list)
                        setDataLoadingFalse()

                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    /**
     * To save states for ExpandableGroups, on navigating back & on app reload
     */

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

    /**
     *MultiSelectionMode
     */

    private fun activateMultiSelectionMode() {
        setMultiSelectionModeToTrue()
        setToolbarState(ShoppingListToolbarState.MultiSelectionState)
    }

    private fun disableMultiSelectionMode() {
        setMultiSelectionModeToFalse()
        clearSelectedRecipes()
        removeSelectedRecipesFromList()
        setToolbarState(ShoppingListToolbarState.SearchState)
    }

    private fun addOrRemoveRecipeFromSelectedList(position: Int, recipe: Recipe) {
        shoppingListInteractionManager.addOrRemoveRecipeFromSelectedList(recipe)
        shoppingListInteractionManager.addOrRemoveRecipePositionFromSelectedList(position)
    }

    private fun deleteSelectedRecipes() {
        if(getSelectedRecipes().size > 0) {
            deleteMultipleRecipesFromShoppingList.execute(getSelectedRecipes()).launchIn(viewModelScope)
            removeSelectedRecipesFromList()
            removeSelectedRecipePositionsFromList()
        }
    }

    /**
     * Supporting Functions
     */

    private fun setDataLoadingTrue() {
        state.value?.dataLoading = true
    }

    private fun setDataLoadingFalse() {
        state.value?.dataLoading = false
    }

    private fun setToolbarState(shoppingListToolbarState: ShoppingListToolbarState) {
        shoppingListInteractionManager.setToolbarState(shoppingListToolbarState)
    }

    private fun getSelectedRecipes() = shoppingListInteractionManager.getSelectedRecipes()

    fun getSelectedRecipesPosition() = shoppingListInteractionManager.getSelectedRecipesPosition()

    private fun removeSelectedRecipesFromList() {
        state.value?.recipeList?.removeAll(getSelectedRecipes())
        clearSelectedRecipes()
    }

    private fun setMultiSelectionModeToTrue() {
        for(recipe in state.value?.recipeList!!) {
            recipe.isMultiSelectionModeEnabled = true
        }
    }

    private fun setMultiSelectionModeToFalse() {
        for(recipe in state.value?.recipeList!!) {
            recipe.isMultiSelectionModeEnabled = false
        }
    }

    private fun clearSelectedRecipes() {
        shoppingListInteractionManager.clearSelectedRecipes()
    }

    private fun removeSelectedRecipePositionsFromList() {
        shoppingListInteractionManager.clearSelectedRecipesPosition()
    }

}