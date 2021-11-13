package dev.zidali.recipeapp.interactors.shared

import dev.zidali.recipeapp.business.datasource.cache.main.FavoriteRecipeDao
import dev.zidali.recipeapp.business.datasource.cache.main.toFavoriteEntity
import dev.zidali.recipeapp.business.interactors.main.shared.DeleteRecipeFromFavorite
import dev.zidali.recipeapp.datasource.cache.AppDatabaseFake
import dev.zidali.recipeapp.datasource.cache.FavoriteRecipeDaoFake
import dev.zidali.recipeapp.datasource.testfakes.TenDuplicateRecipes
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeleteRecipeFromFavoriteTest {

    private val appDataBase = AppDatabaseFake()

    //system in test
    private lateinit var deleteRecipeFromFavorite: DeleteRecipeFromFavorite

    //dependencies
    private lateinit var favoriteRecipeDao: FavoriteRecipeDao

    @BeforeEach
    fun setup() {

        favoriteRecipeDao = FavoriteRecipeDaoFake(appDataBase)

        deleteRecipeFromFavorite = DeleteRecipeFromFavorite(
            favoriteRecipeDao
        )

    }

    @Test
    fun success_1Recipe() = runBlocking {

        val recipes = TenDuplicateRecipes.recipes
        val cachedRecipe = favoriteRecipeDao.getAllRecipes()
        assert(cachedRecipe.isEmpty())
        favoriteRecipeDao.insertRecipes(recipes.map { it.toFavoriteEntity() })
        assert(cachedRecipe.size == 10)

        deleteRecipeFromFavorite.execute(
            recipes[0]
        ).toList()

        assert(cachedRecipe.size == 9)

    }

}