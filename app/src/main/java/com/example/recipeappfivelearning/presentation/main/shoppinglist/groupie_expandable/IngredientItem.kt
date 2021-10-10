package com.example.recipeappfivelearning.presentation.main.shoppinglist.groupie_expandable

import android.graphics.drawable.Animatable
import android.view.View
import com.example.recipeappfivelearning.R
import com.example.recipeappfivelearning.databinding.ShoppingListChildBinding
import com.xwray.groupie.viewbinding.BindableItem
import com.xwray.groupie.viewbinding.GroupieViewHolder

class IngredientItem(
    private val ingredient: String,
    private val onFavoriteListener: (item: IngredientItem, favorite: Boolean) -> Unit
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
            childTextTitle.text = ingredient
            bindCheckBox(viewBinding)
            ingredientBought.setOnClickListener {
                onFavoriteListener(this@IngredientItem, !checked)
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
}