package us.zidali.recipeapp.business.datasource.cache.main.apikey

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import us.zidali.recipeapp.business.domain.models.ApiKey

@Entity(tableName = "apikey")
data class ApiKeyEntity (

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "app_id")
    var app_id: String,

    @ColumnInfo(name = "app_key")
    var app_key: String,

)

fun ApiKey.toApiKeyEntity(): ApiKeyEntity {
    return ApiKeyEntity(
        app_id = app_id,
        app_key = app_key,
    )
}

fun ApiKeyEntity.toApiKey(): ApiKey{
    return ApiKey(
        app_id = app_id,
        app_key = app_key,
    )
}