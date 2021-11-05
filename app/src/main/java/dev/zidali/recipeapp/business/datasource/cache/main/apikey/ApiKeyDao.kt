package dev.zidali.recipeapp.business.datasource.cache.main.apikey

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ApiKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    //if successful returns row #, if not returns -1
    suspend fun insertApiKey(key: ApiKeyEntity): Long

    @Query("DELETE FROM apikey")
    suspend fun deleteAllApiKey()

    @Query("SELECT * FROM apikey")
    suspend fun getApiKey(): ApiKeyEntity

}