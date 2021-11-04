package us.zidali.recipeapp.business.datasource.cache.main.search

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import us.zidali.recipeapp.business.domain.models.Recipe
import us.zidali.recipeapp.business.domain.util.Converters

@Entity(tableName = "temporaryRecipe")
data class TemporaryRecipeEntity (

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

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean,

    @ColumnInfo(name = "timeSaved")
    var timeSaved: String? = null,

)

fun Recipe.toTemporaryEntity(): TemporaryRecipeEntity {
    return TemporaryRecipeEntity(
        recipeName = recipeName!!,
        recipeImageUrl = recipeImageUrl!!,
        recipeIngredients = Converters.convertIngredientListToString(recipeIngredients!!),
        recipeId = recipeId!!,
        recipeCalories = recipeCalories!!,
        isFavorite = isFavorite,
        timeSaved = timeSaved,
    )
}

fun TemporaryRecipeEntity.toTemporaryRecipe(): Recipe{
    return Recipe(
        recipeName = recipeName,
        recipeImageUrl = recipeImageUrl,
        recipeIngredients = Converters.convertIngredientsToList(recipeIngredients),
        recipeId = recipeId,
        recipeCalories = recipeCalories,
        isFavorite = isFavorite,
        timeSaved = timeSaved,
    )
}