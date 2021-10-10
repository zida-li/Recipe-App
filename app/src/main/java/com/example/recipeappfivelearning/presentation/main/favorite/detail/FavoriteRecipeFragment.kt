package com.example.recipeappfivelearning.presentation.main.favorite.detail

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.recipeappfivelearning.R
import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.business.domain.util.Converters
import com.example.recipeappfivelearning.databinding.FragmentViewRecipeBinding
import com.example.recipeappfivelearning.presentation.main.favorite.BaseFavoriteFragment

class FavoriteRecipeFragment: BaseFavoriteFragment() {

    private val requestOptions = RequestOptions
        .placeholderOf(R.drawable.empty_plate)
        .error(R.drawable.empty_plate)

    private val viewModel: FavoriteRecipeViewModel by viewModels()

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
        showAddToShoppingListButton()

        binding.addToShoppingListButton.setOnClickListener{
            viewModel.onTriggerEvent(FavoriteRecipeEvents.AddToShoppingList)
        }
    }

    private fun showAddToShoppingListButton() {
        binding.addToShoppingListButton.visibility = View.VISIBLE
    }

    private fun subscribeObservers() {
        viewModel.state.observe(viewLifecycleOwner, {state->

            state.recipe?.let { setRecipeProperties(it) }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorite_recipe_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_delete_favorite_detail -> {
                viewModel.onTriggerEvent(FavoriteRecipeEvents.DeleteRecipe)
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
        binding.detailRecipeIngredients.text = Converters.convertIngredientListToString(recipe.recipeIngredients!!)
    }
}