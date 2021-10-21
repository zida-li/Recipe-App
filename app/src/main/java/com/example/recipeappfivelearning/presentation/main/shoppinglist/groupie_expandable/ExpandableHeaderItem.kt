package com.example.recipeappfivelearning.presentation.main.shoppinglist.groupie_expandable

import android.graphics.Color
import android.graphics.drawable.Animatable
import android.util.Log
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.example.recipeappfivelearning.R
import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.databinding.ShoppingListParentBinding
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.viewbinding.BindableItem

class ExpandableHeaderItem(
    private val recipe: Recipe,
    private val lifecycleOwner: LifecycleOwner,
    private val interaction: Interaction,
    private val selectedRecipe: LiveData<ArrayList<Recipe>>,
) : BindableItem<ShoppingListParentBinding?>(), ExpandableItem {

    private var expandableGroup: ExpandableGroup? = null

    private lateinit var mRecipe: Recipe

    override fun bind(viewBinding: ShoppingListParentBinding, position: Int) {

        viewBinding.apply {
            icon.visibility = View.VISIBLE
            parentTextTitle.text = recipe.recipeName
            icon.setImageResource(if (expandableGroup!!.isExpanded) R.drawable.collapse else R.drawable.expand)
            icon.setOnClickListener{
                expandableGroup!!.onToggleExpanded()
                bindIcon(viewBinding)
                interaction.expand(expandableGroup!!.isExpanded, recipe)
            }
            shoppingListCardView.setOnClickListener{
                interaction.onItemSelected(position, recipe)
            }
            shoppingListCardView.setOnLongClickListener {
                interaction.activateMultiSelectionMode()
                interaction.onItemSelected(position, recipe)
                true
            }

            mRecipe = recipe

            selectedRecipe.observe(lifecycleOwner, {recipe->
                if (recipe != null) {
                    if (recipe.contains(mRecipe)) {
                        shoppingListCardView.setBackgroundColor(Color.RED)
                    }
                    else {
                        shoppingListCardView.setBackgroundColor(Color.WHITE)
                    }
                } else {
                    shoppingListCardView.setBackgroundColor(Color.WHITE)
                }
            })
        }
    }

    private fun bindIcon(viewBinding: ShoppingListParentBinding) {
        viewBinding.apply {
            icon.visibility = View.VISIBLE
            icon.setImageResource(if(expandableGroup!!.isExpanded) R.drawable.collapse_animated else R.drawable.expand_animated)
            val drawable = icon.drawable as Animatable
            drawable.start()
        }
    }

    override fun getLayout(): Int {
        return R.layout.shopping_list_parent
    }

    override fun initializeViewBinding(view: View): ShoppingListParentBinding = ShoppingListParentBinding.bind(view)

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        expandableGroup = onToggleListener
    }

    interface Interaction {

        fun onItemSelected(position: Int, item: Recipe)

        fun activateMultiSelectionMode()

        fun isMultiSelectionModeEnabled(): Boolean

        fun isRecipeSelected(recipe: Recipe): Boolean

        fun expand(isExpanded: Boolean, recipe: Recipe)

    }


}