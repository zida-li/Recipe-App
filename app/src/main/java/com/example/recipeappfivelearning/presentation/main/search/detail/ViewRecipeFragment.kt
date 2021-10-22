package com.example.recipeappfivelearning.presentation.main.search.detail

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.recipeappfivelearning.R
import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.business.domain.models.groupie.IngredientItem
import com.example.recipeappfivelearning.business.domain.util.Converters
import com.example.recipeappfivelearning.databinding.FragmentViewRecipeBinding
import com.example.recipeappfivelearning.presentation.main.favorite.detail.FavoriteRecipeEvents
import com.example.recipeappfivelearning.presentation.main.search.BaseSearchFragment
import com.xwray.groupie.GroupieAdapter

class ViewRecipeFragment: BaseSearchFragment() {

    private val requestOptions = RequestOptions
        .placeholderOf(R.drawable.empty_plate)
        .error(R.drawable.empty_plate)

    private val viewModel: ViewRecipeViewModel by viewModels()
    private lateinit var groupAdapter: GroupieAdapter

    private var _binding: FragmentViewRecipeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewRecipeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        subscribeObservers()
        initGroupieAdapter()
        initRecyclerView()

        binding.addToShoppingListButton.setOnClickListener{

            if(viewModel.state.value?.recipe!!.isInShoppingList) {
                viewModel.state.value?.recipe!!.isInShoppingList = false
                refreshButtonState()
                viewModel.onTriggerEvent(ViewRecipeEvents.DeleteRecipeFromShoppingList)
            } else {
                viewModel.state.value?.recipe!!.isInShoppingList = true
                viewModel.state.value?.recipe!!.isFavorite = true
                adaptViewToFavoriteIcon()
                refreshButtonState()
                viewModel.onTriggerEvent(ViewRecipeEvents.AddToShoppingList)
            }
        }
    }

    override fun onResume() {
        if(viewModel.state.value?.recipe != null) {
            viewModel.onTriggerEvent(ViewRecipeEvents.CompareSearchToShoppingList)
            refreshButtonState()
        }
        super.onResume()
    }

    private fun initRecyclerView() {
        binding.detailRecipeIngredients.apply {
            layoutManager = LinearLayoutManager(this@ViewRecipeFragment.context)
            adapter = groupAdapter
        }
    }

    private fun initGroupieAdapter() {
        groupAdapter = GroupieAdapter()
    }

    private fun showAddToShoppingListButton() {
        binding.addToShoppingListButton.setText(R.string.add_to_shoppingList)
    }

    private fun showDeleteToShoppingListButton() {
        binding.addToShoppingListButton.setText(R.string.delete_from_shoppingList)
    }

    private fun refreshButtonState() {
//        Log.d(TAG, "refreshButtonState: ${viewModel.state.value?.recipe!!.isInShoppingList}")
        if(viewModel.state.value?.recipe!!.isInShoppingList) {
            showDeleteToShoppingListButton()
        } else {
            showAddToShoppingListButton()
        }
    }

    private fun subscribeObservers() {
        viewModel.state.observe(viewLifecycleOwner, {state->

            state.recipe?.let {
                setRecipeProperties(it)
                refreshButtonState()
            }

            groupAdapter.apply {
                if(state.recipe?.recipeIngredients != null) {
                    for (ingredient in state.recipe?.recipeIngredients!!) {
                        add(IngredientItem(ingredient))
                    }
                }
            }



            if(state.recipe?.isFavorite == true) {
                adaptViewToFavoriteIcon()
            } else {
                adaptViewToNotFavoriteIcon()
            }

        })

    }

    private fun adaptViewToFavoriteIcon() {
        activity?.invalidateOptionsMenu()
    }

    private fun adaptViewToNotFavoriteIcon() {
        activity?.invalidateOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipe_detail_menu, menu)
        if(viewModel.state.value?.recipe?.isFavorite == true) {
            menu.findItem(R.id.isFavorite).isVisible = true
            menu.findItem(R.id.isNotFavorite).isVisible = false
        }
        if(viewModel.state.value?.recipe?.isFavorite == false){
            menu.findItem(R.id.isFavorite).isVisible = false
            menu.findItem(R.id.isNotFavorite).isVisible = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.isFavorite -> {
                viewModel.onTriggerEvent(ViewRecipeEvents.DeleteRecipe)
                adaptViewToFavoriteIcon()
                return true
            }
            R.id.isNotFavorite -> {
                viewModel.onTriggerEvent(ViewRecipeEvents.SaveRecipe)
                adaptViewToNotFavoriteIcon()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setRecipeProperties(recipe: Recipe) {
        Glide.with(this)
            .setDefaultRequestOptions(requestOptions)
            .load(recipe.recipeImageUrl)
            .into(binding.detailRecipeImage)
        binding.detailRecipeTitle.text = recipe.recipeName
    }
}