package com.example.recipeappfivelearning.presentation.main.search.detail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.recipeappfivelearning.R
import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.business.domain.util.Converters
import com.example.recipeappfivelearning.databinding.FragmentViewRecipeBinding
import com.example.recipeappfivelearning.presentation.main.search.detail.viewmodel.ViewRecipeViewModel
import com.example.recipeappfivelearning.presentation.main.search.list.BaseSearchFragment

class ViewRecipeFragment: BaseSearchFragment() {

    private val requestOptions = RequestOptions
        .placeholderOf(R.drawable.empty_plate)
        .error(R.drawable.empty_plate)

    private val viewModel: ViewRecipeViewModel by viewModels()

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
    }

    private fun subscribeObservers() {
        viewModel.state.observe(viewLifecycleOwner, {state->

            state.recipe?.let { setRecipeProperties(it) }

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



    private fun setRecipeProperties(recipe: Recipe) {
        Glide.with(this)
            .setDefaultRequestOptions(requestOptions)
            .load(recipe.recipeImageUrl)
            .into(binding.detailRecipeImage)
        binding.detailRecipeTitle.text = recipe.recipeName
        binding.detailRecipeIngredients.text = Converters.convertIngredientListToString(recipe.recipeIngredients!!)
    }
}