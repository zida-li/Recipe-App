package com.example.recipeappfivelearning.presentation.main.favorite.list

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeappfivelearning.R
import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.business.domain.util.MessageType
import com.example.recipeappfivelearning.business.domain.util.Response
import com.example.recipeappfivelearning.business.domain.util.StateMessage
import com.example.recipeappfivelearning.business.domain.util.UIComponentType
import com.example.recipeappfivelearning.databinding.FragmentFavoriteBinding
import com.example.recipeappfivelearning.presentation.main.favorite.BaseFavoriteFragment
import com.example.recipeappfivelearning.presentation.util.TopSpacingItemDecoration

class FavoriteFragment : BaseFavoriteFragment(),
    FavoriteListAdapter.Interaction
{

    private val viewModel: FavoriteViewModel by viewModels()
    private var recyclerAdapter: FavoriteListAdapter? = null

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initRecyclerView()
        subscribeObservers()
    }

    override fun onResume() {
        viewModel.onTriggerEvent(FavoriteEvents.FetchFavoriteRecipes)
        super.onResume()
    }

    private fun subscribeObservers() {

        viewModel.state.observe(viewLifecycleOwner, {state->

            recyclerAdapter?.apply {
                submitList(
                    recipeList = state.recipeList
                )
            }

        })

        viewModel.toolbarState.observe(viewLifecycleOwner, {toolbarState->
            when(toolbarState) {
                is FavoriteListToolbarState.MultiSelectionState -> {
                    enableMultiSelectToolbarState()

                }
                is FavoriteListToolbarState.SearchState -> {
                    disableMultiSelectToolbarState()
                }
            }
        })
    }

    /*
       Enable MultiSelection State
     */

    private fun enableMultiSelectToolbarState() {
        activity?.invalidateOptionsMenu()
    }

    /*
        Disable MultiSelection State
     */

    private fun disableMultiSelectToolbarState() {
        viewModel.onTriggerEvent(FavoriteEvents.ClearSelectedRecipes)
        activity?.invalidateOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if(isMultiSelectionModeEnabled()) {
            inflater.inflate(R.menu.favorite_fragment_multiselection_menu, menu)
        } else {
            inflater.inflate(R.menu.favorite_fragment_menu, menu)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.action_delete_favorite_fragment -> {
                viewModel.onTriggerEvent(FavoriteEvents.DeleteSelectedRecipes)
                recyclerAdapter?.notifyDataSetChanged()
            }
            R.id.action_exit_multiselect_state_favoritefragment -> {
                viewModel.onTriggerEvent(FavoriteEvents.SetToolBarState(FavoriteListToolbarState.SearchState))
            }
            R.id.action_grid_view_favorite_fragment -> {
                initGridView()
            }
            R.id.action_list_view_favorite_fragment -> {
                initListView()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initRecyclerView() {
        binding.fragmentFavoriteRecyclerview.apply {
            layoutManager = GridLayoutManager(this@FavoriteFragment.context, 2)
            val topSpacingDecorator = TopSpacingItemDecoration(30)
            removeItemDecoration(topSpacingDecorator)
            addItemDecoration(topSpacingDecorator)

            recyclerAdapter = FavoriteListAdapter(this@FavoriteFragment,
                viewLifecycleOwner,
                viewModel.favoriteListInteractionManager.selectedRecipe)

            adapter = recyclerAdapter
        }
    }

    private fun initGridView() {
        initRecyclerView()
    }

    private fun initListView() {
        binding.fragmentFavoriteRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@FavoriteFragment.context)
            val topSpacingDecorator = TopSpacingItemDecoration(30)
            removeItemDecoration(topSpacingDecorator)
            addItemDecoration(topSpacingDecorator)

            recyclerAdapter = FavoriteListAdapter(this@FavoriteFragment,
                viewLifecycleOwner,
                viewModel.favoriteListInteractionManager.selectedRecipe)

            adapter = recyclerAdapter
        }
    }

    override fun onItemSelected(position: Int, item: Recipe) {
        if(isMultiSelectionModeEnabled()) {
            viewModel.onTriggerEvent(FavoriteEvents.AddOrRemoveRecipeFromSelectedList(item))
        }
        else {
            try {
                viewModel.state.value?.let { state ->
                    val bundle = bundleOf("favoriteRecipeName" to item.recipeName)
                    findNavController().navigate(
                        R.id.action_favoriteFragment_to_favoriteRecipeFragment,
                        bundle
                    )
                } ?: throw Exception("Null Recipe")
            } catch (e: Exception) {
                e.printStackTrace()
                viewModel.onTriggerEvent(
                    FavoriteEvents.Error(
                        stateMessage = StateMessage(
                            response = Response(
                                message = e.message,
                                uiComponentType = UIComponentType.Dialog,
                                messageType = MessageType.Error
                            )
                        )
                    )
                )
            }
        }
    }

    override fun activateMultiSelectionMode() {
        viewModel.onTriggerEvent(FavoriteEvents.SetToolBarState(FavoriteListToolbarState.MultiSelectionState))
    }

    override fun isMultiSelectionModeEnabled()
    = viewModel.favoriteListInteractionManager.isMultiSelectionStateActive()


    override fun isRecipeSelected(recipe: Recipe): Boolean {
        return viewModel.isRecipeSelected(recipe)
    }
}