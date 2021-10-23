package com.example.recipeappfivelearning.presentation.main.shoppinglist.groupie_expandable

import android.view.View
import com.example.recipeappfivelearning.R
import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.databinding.ShoppingListChildBinding
import com.xwray.groupie.Item
import com.xwray.groupie.viewbinding.BindableItem

class IngredientItem(
    private val ingredient: Recipe.Ingredient,
    private val interaction: Interaction,
    private val isChecked: Boolean,
    private val onFavoriteListener: (item: IngredientItem, favorite: Boolean) -> Unit,
): BindableItem<ShoppingListChildBinding?>() {

    companion object {
        const val FAVORITE = "FAVORITE"
    }

    private var checked = false

    override fun getLayout(): Int {
        return R.layout.shopping_list_child
    }

    override fun initializeViewBinding(view: View): ShoppingListChildBinding = ShoppingListChildBinding.bind(view)

    override fun bind(viewBinding: ShoppingListChildBinding, position: Int) {
        viewBinding.apply {
            childTextTitle.text = ingredient.recipeIngredient
            setFavoriteOnLoad(isChecked)
            bindCheckBox(viewBinding)
            ingredientBought.setOnClickListener {
                onFavoriteListener(this@IngredientItem, !checked)
                interaction.onIsCheckedClicked(ingredient)
            }
        }
    }

    private fun bindCheckBox(binding: ShoppingListChildBinding) {
        binding.ingredientBought.setImageResource(R.drawable.shoppinglist_state)
        binding.ingredientBought.isChecked = checked
    }

    fun setFavorite(favorite: Boolean) {
        checked = favorite
    }

    fun setFavoriteOnLoad(favorite: Boolean) {
        checked = favorite
    }

    override fun bind(
        viewBinding: ShoppingListChildBinding,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if(payloads.contains(FAVORITE)){
            bindCheckBox(viewBinding)
        } else {
            bind(viewBinding, position)
        }
    }

    interface Interaction {

        fun onIsCheckedClicked(item: Recipe.Ingredient)

    }
}