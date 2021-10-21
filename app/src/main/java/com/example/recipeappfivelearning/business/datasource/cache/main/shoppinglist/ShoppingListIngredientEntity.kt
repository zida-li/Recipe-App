package com.example.recipeappfivelearning.business.datasource.cache.main.shoppinglist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipeappfivelearning.business.domain.models.Recipe

@Entity(tableName = "shoppingListIngredient")
data class ShoppingListIngredientEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "ingredient")
    var recipeIngredient: String,

    @ColumnInfo(name = "label")
    var recipeName: String,

    @ColumnInfo(name = "isChecked")
    var isChecked: Boolean,

)

fun Recipe.toShoppingListIngredientEntity(): ShoppingListIngredientEntity{
    return ShoppingListIngredientEntity(
        recipeIngredient = recipeIngredient!!,
        recipeName = recipeName!!,
        isChecked = false,
    )
}

fun ShoppingListIngredientEntity.toRecipeIngredients(): Recipe.Ingredient {
    return Recipe.Ingredient(
        recipeName = recipeName,
        recipeIngredient = recipeIngredient,
        isChecked = isChecked
    )
}

fun Recipe.Ingredient.toShoppingListIngredientEntity(): ShoppingListIngredientEntity{
    return ShoppingListIngredientEntity(
        recipeIngredient = recipeIngredient,
        recipeName = recipeName,
        isChecked = isChecked,
    )
}