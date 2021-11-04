package us.zidali.recipeapp.presentation.main.favorite.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import us.zidali.recipeapp.business.domain.models.Recipe

class FavoriteListInteractionManager {

    private val _selectedRecipe: MutableLiveData<ArrayList<Recipe>> = MutableLiveData()

    private val _toolbarState: MutableLiveData<FavoriteListToolbarState>
            = MutableLiveData(FavoriteListToolbarState.SearchState)

    val selectedRecipe: LiveData<ArrayList<Recipe>>
        get() = _selectedRecipe

    val toolbarState: LiveData<FavoriteListToolbarState>
        get() = _toolbarState

    fun setToolbarState(state: FavoriteListToolbarState){
        _toolbarState.value = state
    }

    fun getSelectedRecipes():ArrayList<Recipe> = _selectedRecipe.value?: ArrayList()

    fun isMultiSelectionStateActive(): Boolean{
        return _toolbarState.value.toString() == FavoriteListToolbarState.MultiSelectionState.toString()
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

    fun isRecipeSelected(recipe: Recipe): Boolean{
        return _selectedRecipe.value?.contains(recipe)?: false
    }

    fun clearSelectedRecipes(){
        _selectedRecipe.value = null
    }

}