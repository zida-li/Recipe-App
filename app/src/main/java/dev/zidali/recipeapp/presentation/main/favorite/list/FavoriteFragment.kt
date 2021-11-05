package dev.zidali.recipeapp.presentation.main.favorite.list

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dev.zidali.recipeapp.R
import dev.zidali.recipeapp.business.domain.models.Recipe
import dev.zidali.recipeapp.business.domain.util.*
import dev.zidali.recipeapp.databinding.FragmentFavoriteBinding
import dev.zidali.recipeapp.presentation.main.favorite.BaseFavoriteFragment
import dev.zidali.recipeapp.presentation.util.TopSpacingItemDecoration
import dev.zidali.recipeapp.presentation.util.processQueue

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

            processQueue(
                context = context,
                queue = state.queue,
                stateMessageCallback = object: StateMessageCallback {
                    override fun removeMessageFromStack() {
                        viewModel.onTriggerEvent(FavoriteEvents.OnRemoveHeadFromQueue)
                    }
                }
            )

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if(isMultiSelectionModeEnabled()) {
            inflater.inflate(R.menu.favorite_fragment_multiselection_menu, menu)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.action_delete_favorite_fragment -> {
                confirmDeleteRequest()
            }
            R.id.action_exit_multiselect_state_favoritefragment -> {
                viewModel.onTriggerEvent(FavoriteEvents.SetToolBarState(FavoriteListToolbarState.SearchState))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Recipe Functions
     */

    private fun confirmDeleteRequest() {
        val callback: AreYouSureCallback = object: AreYouSureCallback {

            override fun proceed() {
                viewModel.onTriggerEvent(FavoriteEvents.DeleteSelectedRecipes)
                recyclerAdapter?.notifyDataSetChanged()
            }

            override fun cancel() {
                //do nothing
            }
        }
        viewModel.onTriggerEvent(FavoriteEvents.AppendToMessageQueue(
            stateMessage = StateMessage(
                response = Response(
                    message = "Are You Sure? This cannot be undone",
                    uiComponentType = UIComponentType.AreYouSureDialog(callback),
                    messageType = MessageType.Info
                )
            )
        ))
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
                    FavoriteEvents.AppendToMessageQueue(
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

    /**
     * MultiSelectMode
     */

    private fun enableMultiSelectToolbarState() {
        activity?.invalidateOptionsMenu()
    }

    private fun disableMultiSelectToolbarState() {
        viewModel.onTriggerEvent(FavoriteEvents.ClearSelectedRecipes)
        activity?.invalidateOptionsMenu()
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