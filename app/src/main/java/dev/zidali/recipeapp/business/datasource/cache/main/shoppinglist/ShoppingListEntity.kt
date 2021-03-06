package dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.zidali.recipeapp.business.domain.models.Recipe
import dev.zidali.recipeapp.business.domain.util.Converters

@Entity(tableName = "shoppingList")
data class ShoppingListEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "label")
    var recipeName: String,

    @ColumnInfo(name = "image")
    var recipeImageUrl: String,

    @ColumnInfo(name = "ingredientLines")
    var recipeIngredients: String,

    @ColumnInfo(name = "uri")
    var recipeId: String,

    @ColumnInfo(name = "calories")
    var recipeCalories: Float,

    @ColumnInfo(name = "timeSaved")
    var timeSaved: String? = null,

    @ColumnInfo(name = "isExpanded")
    var isExpanded: Boolean,

    @ColumnInfo(name = "isInShoppingList")
    var isInShoppingList: Boolean,

    @ColumnInfo(name = "isMultiSelectionModeEnabled")
    var isMultiSelectionModeEnabled: Boolean,

)

fun Recipe.toShoppingListEntity(): ShoppingListEntity{
    return ShoppingListEntity(
        recipeName = recipeName!!,
        recipeImageUrl = recipeImageUrl!!,
        recipeIngredients = Converters.convertIngredientListToString(recipeIngredients!!),
        recipeId = recipeId!!,
        recipeCalories = recipeCalories!!,
        timeSaved = timeSaved,
        isExpanded = isExpanded,
        isInShoppingList = isInShoppingList,
        isMultiSelectionModeEnabled = isMultiSelectionModeEnabled,
    )
}

fun ShoppingListEntity.toShoppingListRecipe(): Recipe {
    return Recipe(
        recipeName = recipeName!!,
        recipeImageUrl = recipeImageUrl!!,
        recipeIngredients = Converters.convertIngredientsToList(recipeIngredients),
        recipeId = recipeId!!,
        recipeCalories = recipeCalories!!,
        timeSaved = timeSaved,
        isExpanded = isExpanded,
        isInShoppingList = isInShoppingList,
        isMultiSelectionModeEnabled = isMultiSelectionModeEnabled,
    )
}