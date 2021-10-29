package com.example.recipeappfivelearning.presentation.main.shoppinglist.groupie_expandable

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Animatable
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.example.recipeappfivelearning.R
import com.example.recipeappfivelearning.business.domain.models.Recipe
import com.example.recipeappfivelearning.databinding.ShoppingListParentBinding
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.Item
import com.xwray.groupie.viewbinding.BindableItem

class ExpandableHeaderItem(
    private val recipe: Recipe,
    private val lifecycleOwner: LifecycleOwner,
    private val interaction: Interaction,
    private val selectedRecipe: LiveData<ArrayList<Recipe>>,
    private val context: Context,
    private val isMultiSelectionModeEnabled: Boolean,
) : BindableItem<ShoppingListParentBinding?>(), ExpandableItem {

    private var expandableGroup: ExpandableGroup? = null

    private lateinit var mRecipe: Recipe

    override fun bind(viewBinding: ShoppingListParentBinding, position: Int) {

        viewBinding.apply {
            icon.visibility = View.VISIBLE
            parentTextTitle.text = recipe.recipeName
            icon.setImageResource(if (expandableGroup!!.isExpanded) R.drawable.collapse else R.drawable.expand)
            Log.d("AppDebug", "ExpandableHeaderItem: isMultiEnabled: $isMultiSelectionModeEnabled")
            shoppingListCardView.setOnClickListener{
                if (!isMultiSelectionModeEnabled) {
                    expandableGroup!!.onToggleExpanded()
                    bindIcon(viewBinding)
                }
                interaction.expand(expandableGroup!!.isExpanded, recipe)
                interaction.onItemSelected(position, recipe)
            }
            shoppingListCardView.setOnLongClickListener {
                interaction.activateMultiSelectionMode()
                interaction.onItemSelected(position, recipe)
                true
            }

            mRecipe = recipe
            Log.d("AppDebug", "ExpandableHeaderItem: mRecipe: ${mRecipe.recipeName}")

            selectedRecipe.observe(lifecycleOwner, {recipe->

                if(recipe != null) {
                    for (r in recipe) {
                        Log.d("AppDebug", "ExpandableHeaderItem: selectedRecipe: ${r.recipeName}")
                    }
                }

                if (recipe != null) {
                    if (recipe.contains(mRecipe)) {
                        Log.d("AppDebug", "ExpandableHeaderItem: match found: ${mRecipe.recipeName}")
                        shoppingListCardView.setBackgroundColor(ContextCompat.getColor(context, R.color.primaryColor))
                    }
                    else {
                        shoppingListCardView.setBackgroundColor(Color.WHITE)
                        Log.d("AppDebug", "ExpandableHeaderItem: else1")
                    }
                } else {
                    shoppingListCardView.setBackgroundColor(Color.WHITE)
                    Log.d("AppDebug", "ExpandableHeaderItem: else2")
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

    override fun isSameAs(other: Item<*>): Boolean {
        if (other !is ExpandableHeaderItem) return false
        return recipe.recipeId == other.recipe.recipeId
    }

    override fun hasSameContentAs(other: Item<*>): Boolean {
        if (other !is ExpandableHeaderItem) return false
        return recipe == other.recipe
    }

    interface Interaction {

        fun onItemSelected(position: Int, item: Recipe)

        fun activateMultiSelectionMode()

        fun isMultiSelectionModeEnabled(): Boolean

        fun expand(isExpanded: Boolean, recipe: Recipe)

    }


}