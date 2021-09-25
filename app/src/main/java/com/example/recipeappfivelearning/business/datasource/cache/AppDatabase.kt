package com.example.recipeappfivelearning.business.datasource.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recipeappfivelearning.business.datasource.cache.main.search.FavoriteRecipeDao
import com.example.recipeappfivelearning.business.datasource.cache.main.search.FavoriteRecipeEntity
import com.example.recipeappfivelearning.business.datasource.cache.main.search.TemporaryRecipeDao
import com.example.recipeappfivelearning.business.datasource.cache.main.search.TemporaryRecipeEntity

@Database(entities = [FavoriteRecipeEntity::class, TemporaryRecipeEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getTemporaryRecipeDao(): TemporaryRecipeDao

    abstract fun getFavoriteRecipeDao(): FavoriteRecipeDao

    companion object {
        const val DATABASE_NAME = "app_db"
    }

}