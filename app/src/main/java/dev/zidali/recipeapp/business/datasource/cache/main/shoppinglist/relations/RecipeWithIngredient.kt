package dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.relations

import androidx.room.Embedded
import androidx.room.Relation
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListEntity
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListIngredientEntity
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.toRecipeIngredients
import dev.zidali.recipeapp.business.domain.models.Recipe
import dev.zidali.recipeapp.business.domain.util.Converters

data class RecipeWithIngredient (

    @Embedded val recipe: ShoppingListEntity,
    @Relation(
        parentColumn = "label",
        entityColumn = "label"
    )
    val ingredients: MutableList<ShoppingListIngredientEntity>

)

fun RecipeWithIngredient.toRecipeWithIngredient(): Recipe {
    return Recipe(
        recipeName = recipe.recipeName,
        recipeImageUrl = recipe.recipeImageUrl,
        recipeIngredients = Converters.convertIngredientsToList(recipe.recipeIngredients),
        recipeId = recipe.recipeId,
        recipeCalories = recipe.recipeCalories,
        timeSaved = recipe.timeSaved,
        recipeIngredientCheck = ingredients.map { it.toRecipeIngredients() }.toMutableList(),
        isExpanded = recipe.isExpanded,
        isMultiSelectionModeEnabled = recipe.isMultiSelectionModeEnabled,
    )
}