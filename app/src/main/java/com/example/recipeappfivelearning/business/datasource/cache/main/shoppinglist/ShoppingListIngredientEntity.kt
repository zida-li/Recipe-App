package com.example.recipeappfivelearning.business.datasource.cache.main.shoppinglist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shoppingListIngredient")
data class ShoppingListIngredientEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "label")
    var recipeName: String,

    @ColumnInfo(name = "image")
    var recipeIngredient: String,

    @ColumnInfo(name = "isChecked")
    var isChecked: Boolean,

)
