package com.example.recipeappfivelearning.business.domain.models.groupie

import android.view.View
import com.example.recipeappfivelearning.R
import com.example.recipeappfivelearning.databinding.FragmentViewRecipeIngredientcardBinding
import com.xwray.groupie.viewbinding.BindableItem

class IngredientItem(
    val ingredient: String
): BindableItem<FragmentViewRecipeIngredientcardBinding>() {

    override fun bind(viewBinding: FragmentViewRecipeIngredientcardBinding, position: Int) {
        viewBinding.fragmentFavoriteRecipeIngredient.text = ingredient
    }

    override fun getLayout(): Int {
        return R.layout.fragment_view_recipe_ingredientcard
    }

    override fun initializeViewBinding(view: View): FragmentViewRecipeIngredientcardBinding {
        return FragmentViewRecipeIngredientcardBinding.bind(view)
    }

}