package dev.zidali.recipeapp.presentation.main.shoppinglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.zidali.recipeapp.business.domain.models.Recipe

class ShoppingListInteractionManager {

    private val _selectedRecipe: MutableLiveData<ArrayList<Recipe>> = MutableLiveData()

    private val _selectedRecipePosition: MutableLiveData<ArrayList<Int>> = MutableLiveData()

    private val _toolbarState: MutableLiveData<ShoppingListToolbarState>
            = MutableLiveData(ShoppingListToolbarState.SearchState)

    val selectedRecipe: LiveData<ArrayList<Recipe>>
        get() = _selectedRecipe

    val selectedRecipePosition: LiveData<ArrayList<Int>>
        get() = _selectedRecipePosition

    val toolbarState: LiveData<ShoppingListToolbarState>
        get() = _toolbarState

    fun setToolbarState(state: ShoppingListToolbarState){
        _toolbarState.value = state
    }

    fun getSelectedRecipes():ArrayList<Recipe> = _selectedRecipe.value?: ArrayList()

    fun getSelectedRecipesPosition():ArrayList<Int> = _selectedRecipePosition.value?: ArrayList()

    fun isMultiSelectionStateActive(): Boolean{
        return _toolbarState.value.toString() == ShoppingListToolbarState.MultiSelectionState.toString()
    }

    fun addOrRemoveRecipeFromSelectedList(recipe: Recipe){
        var list = _selectedRecipe.value
        if(list == null){
            list = ArrayList()
        }
        if (list.contains(recipe)){
            list.remove(recipe)
        }
        else{
            list.add(recipe)
        }
        _selectedRecipe.value = list
    }

    fun addOrRemoveRecipePositionFromSelectedList(position: Int) {
        var recipePosition = _selectedRecipePosition.value
        if(recipePosition == null){
            recipePosition = ArrayList()
        }
        if (recipePosition.contains(position)){
            recipePosition.remove(position)
        }
        else{
            recipePosition.add(position)
        }
        _selectedRecipePosition.value = recipePosition
    }

    fun isRecipeSelected(recipe: Recipe): Boolean{
        return _selectedRecipe.value?.contains(recipe)?: false
    }

    fun clearSelectedRecipes(){
        _selectedRecipe.value = null
    }

    fun clearSelectedRecipesPosition() {
        _selectedRecipePosition.value = null
    }

}