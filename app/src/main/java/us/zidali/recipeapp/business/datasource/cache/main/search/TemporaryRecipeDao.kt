package us.zidali.recipeapp.business.datasource.cache.main.search

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TemporaryRecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    //if successful returns row #, if not returns -1
    suspend fun insertRecipe(recipe: TemporaryRecipeEntity): Long

    @Query("SELECT * FROM temporaryRecipe WHERE label = :label")
    suspend fun getRecipeByLabel(label: String): TemporaryRecipeEntity

    @Query("DELETE FROM temporaryRecipe")
    suspend fun deleteAllRecipes()

}