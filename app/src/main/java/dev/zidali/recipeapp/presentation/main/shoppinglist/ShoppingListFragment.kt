package dev.zidali.recipeapp.presentation.main.shoppinglist

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dev.zidali.recipeapp.R
import dev.zidali.recipeapp.business.domain.models.Recipe
import dev.zidali.recipeapp.databinding.FragmentShoppingListBinding
import dev.zidali.recipeapp.presentation.main.shoppinglist.expandable.ShoppingListAdapter
import dev.zidali.recipeapp.presentation.util.TopSpacingItemDecoration

class ShoppingListFragment : BaseShoppingListFragment (),
ShoppingListAdapter.Interaction
{

    private val viewModel: ShoppingListViewModel by viewModels()

    private var recyclerAdapter: ShoppingListAdapter? = null
    private var _binding: FragmentShoppingListBinding? = null
    private val binding get() = _binding!!

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
        initRecyclerView()
    }

    override fun onResume() {
        viewModel.onTriggerEvent(ShoppingListEvents.FetchShoppingList)
        super.onResume()
    }

    private fun subscribeObservers() {

        viewModel.state.observe(viewLifecycleOwner, {state->

            recyclerAdapter?.apply {
                submitList(
                    list = state.recipeList
                )
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

    private fun initRecyclerView() {
        binding.shoppingListRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@ShoppingListFragment.context)
            val topSpacingDecorator = TopSpacingItemDecoration(30)
            removeItemDecoration(topSpacingDecorator)
            addItemDecoration(topSpacingDecorator)

            recyclerAdapter = ShoppingListAdapter(
                this@ShoppingListFragment,
                viewLifecycleOwner,
                viewModel.shoppingListInteractionManager.selectedRecipe
            )
            adapter = recyclerAdapter
        }
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
                viewModel.onTriggerEvent(ShoppingListEvents.DeleteSelectedRecipes)
                recyclerAdapter?.notifyDataSetChanged()
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
//        Log.d(TAG, "ShoppingListFragment: onIsCheckedClicked Triggered")
        viewModel.onTriggerEvent(ShoppingListEvents.SetIsCheckedIngredient(item))
    }

}
