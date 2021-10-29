package com.example.recipeappfivelearning.presentation.main.shoppinglist

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeappfivelearning.R
import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.databinding.FragmentShoppingListBinding
import com.example.recipeappfivelearning.presentation.main.shoppinglist.groupie_expandable.ExpandableHeaderItem
import com.example.recipeappfivelearning.presentation.main.shoppinglist.groupie_expandable.IngredientItem
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.GroupieAdapter

class ShoppingListFragment : BaseShoppingListFragment (),
ExpandableHeaderItem.Interaction,
IngredientItem.Interaction {

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
        viewModel.onTriggerEvent(ShoppingListEvents.FetchShoppingList)
        super.onResume()
    }

    private fun subscribeObservers() {

        viewModel.state.observe(viewLifecycleOwner, {state->

            if(state.needToReload) {
                resetAdapterState()
            }

            for (recipe in state.recipeList) {
                val expandableHeaderItem = ExpandableHeaderItem(
                    recipe = recipe,
                    viewLifecycleOwner,
                    this@ShoppingListFragment,
                    viewModel.shoppingListInteractionManager.selectedRecipe,
                    context!!,
                    recipe.isMultiSelectionModeEnabled
                )

            groupAdapter.add(
                ExpandableGroup(
                    expandableHeaderItem,
                    recipe.isExpanded
                ).apply {
                for (ingredient in recipe.recipeIngredientCheck!!) {
                    add(
                        IngredientItem(
                            ingredient,
                            this@ShoppingListFragment,
                            ingredient.isChecked
                        ) { item, favorite ->
                            item.setFavorite(favorite)
                            item.notifyChanged(IngredientItem.FAVORITE)
                        })
                    }
                })
            }
        })

        viewModel.toolbarState.observe(viewLifecycleOwner, {toolbarState->
            when(toolbarState) {
                is ShoppingListToolbarState.MultiSelectionState -> {
                    changeMultiSelectToolbarState()

                }
                is ShoppingListToolbarState.SearchState -> {
                    changeMultiSelectToolbarState()
                }
            }
        })

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

    private fun resetAdapterState() {
        initGroupieAdapter()
        initRecyclerView()
    }

    private fun changeMultiSelectToolbarState() {
        activity?.invalidateOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if(isMultiSelectionModeEnabled()) {
            inflater.inflate(R.menu.shoppinglist_fragment_menu, menu)
        }
        super.onCreateOptionsMenu(menu, inflater)
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
                viewModel.onTriggerEvent(ShoppingListEvents.DisableMultiSelectMode)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun activateMultiSelectionMode() {
        viewModel.onTriggerEvent(ShoppingListEvents.ActivateMultiSelectionMode)
    }

    override fun isMultiSelectionModeEnabled() = viewModel.shoppingListInteractionManager.isMultiSelectionStateActive()

    override fun expand(isExpanded: Boolean, recipe: Recipe) {
        viewModel.onTriggerEvent(ShoppingListEvents.SetIsExpandedRecipe(isExpanded, recipe))
    }

    override fun onItemSelected(position: Int, item: Recipe) {
        if(isMultiSelectionModeEnabled()) {
            viewModel.onTriggerEvent(ShoppingListEvents.AddOrRemoveRecipeFromSelectedList(position, item))
        }
    }

    override fun onIsCheckedClicked(item: Recipe.Ingredient) {
        viewModel.onTriggerEvent(ShoppingListEvents.SetIsCheckedIngredient(item))
    }

}
