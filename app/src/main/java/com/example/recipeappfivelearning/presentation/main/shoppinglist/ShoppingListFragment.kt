package com.example.recipeappfivelearning.presentation.main.shoppinglist

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeappfivelearning.R
import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.databinding.FragmentShoppingListBinding
import com.example.recipeappfivelearning.presentation.main.favorite.list.FavoriteEvents
import com.example.recipeappfivelearning.presentation.main.favorite.list.FavoriteListToolbarState
import com.example.recipeappfivelearning.presentation.main.shoppinglist.groupie_expandable.ExpandableHeaderItem
import com.example.recipeappfivelearning.presentation.main.shoppinglist.groupie_expandable.IngredientItem
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.Group
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.OnItemLongClickListener

class ShoppingListFragment : BaseShoppingListFragment (),
ExpandableHeaderItem.Interaction {

    private val viewModel: ShoppingListViewModel by viewModels()

    private var _binding: FragmentShoppingListBinding? = null
    private val binding get() = _binding!!
    private lateinit var groupAdapter: GroupieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoppingListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        subscribeObservers()
        initGroupieAdapter()
        initRecyclerView()
    }

    override fun onResume() {
        if(viewModel.state.value?.recipeList?.size == 0) {
            viewModel.onTriggerEvent(ShoppingListEvents.FetchShoppingList)
        }
        super.onResume()
    }

    private fun subscribeObservers() {

        viewModel.state.observe(viewLifecycleOwner, {state->

            for(recipe in state.recipeList) {
                val expandableHeaderItem = ExpandableHeaderItem(
                    recipe = recipe,
                    viewLifecycleOwner,
                    this@ShoppingListFragment,
                    viewModel.shoppingListInteractionManager.selectedRecipe
                )

                groupAdapter.apply {
                    groupAdapter.add(ExpandableGroup(expandableHeaderItem).apply {
                        for (ingredient in recipe.recipeIngredients!!) {
                            if (ingredient != "") {
                                add(IngredientItem(ingredient) { item, favorite ->
                                    item.setFavorite(favorite)
                                    item.notifyChanged(IngredientItem.FAVORITE)
                                })
                            }
                        }
                    })
                }
            }

        })

        viewModel.toolbarState.observe(viewLifecycleOwner, {toolbarState->
            when(toolbarState) {
                is ShoppingListToolbarState.MultiSelectionState -> {
                    enableMultiSelectToolbarState()

                }
                is ShoppingListToolbarState.SearchState -> {
                    disableMultiSelectToolbarState()
                }
            }
        })

    }

    private fun enableMultiSelectToolbarState() {
        activity?.invalidateOptionsMenu()
    }

    private fun disableMultiSelectToolbarState() {
        viewModel.onTriggerEvent(ShoppingListEvents.ClearSelectedRecipes)
        viewModel.onTriggerEvent(ShoppingListEvents.ClearSelectedRecipesPosition)
        activity?.invalidateOptionsMenu()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.action_delete_shoppinglist_fragment -> {
                val positions = viewModel.getSelectedRecipesPosition()
                for(position in positions) {
                    groupAdapter.removeGroupAtAdapterPosition(position)
                }
                viewModel.onTriggerEvent(ShoppingListEvents.DeleteSelectedRecipes)
            }
            R.id.action_exit_multiselect_state_shoppinglist -> {
                viewModel.onTriggerEvent(ShoppingListEvents.SetToolbarState(ShoppingListToolbarState.SearchState))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if(isMultiSelectionModeEnabled()) {
            inflater.inflate(R.menu.shoppinglist_fragment_menu, menu)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun initGroupieAdapter() {
        groupAdapter = GroupieAdapter()
    }

    private fun initRecyclerView() {
        binding.shoppingListRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@ShoppingListFragment.context)
            adapter = groupAdapter
        }
    }

    override fun activateMultiSelectionMode() {
        viewModel.onTriggerEvent(ShoppingListEvents.SetToolbarState(ShoppingListToolbarState.MultiSelectionState))
    }

    override fun isMultiSelectionModeEnabled() = viewModel.shoppingListInteractionManager.isMultiSelectionStateActive()

    override fun isRecipeSelected(recipe: Recipe): Boolean {
        return true
    }

    override fun onItemSelected(position: Int, item: Recipe) {
        if(isMultiSelectionModeEnabled()) {
            viewModel.onTriggerEvent(ShoppingListEvents.AddOrRemoveRecipeFromSelectedList(item))
            viewModel.onTriggerEvent(ShoppingListEvents.AddOrRemoveRecipePositionFromSelectedList(position))
        }
    }

}