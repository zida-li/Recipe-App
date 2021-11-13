package dev.zidali.recipeapp.interactors.favorite.list

import dev.zidali.recipeapp.business.datasource.cache.main.FavoriteRecipeDao
import dev.zidali.recipeapp.business.datasource.cache.main.toFavoriteEntity
import dev.zidali.recipeapp.business.interactors.main.favorite.list.DeleteMultipleRecipesFromFavorite
import dev.zidali.recipeapp.datasource.cache.AppDatabaseFake
import dev.zidali.recipeapp.datasource.cache.FavoriteRecipeDaoFake
import dev.zidali.recipeapp.datasource.testfakes.TenDuplicateRecipes
import dev.zidali.recipeapp.datasource.testfakes.ThreeDuplicateRecipes
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeleteRecipesFromFavoriteTest {

    private val appDataBase = AppDatabaseFake()

    //system in test
    private lateinit var deleteMultipleRecipesFromFavorite: DeleteMultipleRecipesFromFavorite

    //dependencies
    private lateinit var cache: FavoriteRecipeDao

    @BeforeEach
    fun setup() {

        cache = FavoriteRecipeDaoFake(appDataBase)

        deleteMultipleRecipesFromFavorite = DeleteMultipleRecipesFromFavorite(
            favoriteRecipeDao = cache
        )
    }

    @Test
    fun success_Delete3Recipe() = runBlocking {

        //verify db is empty, insert 10 recipes, then confirm 10 was inserted
        val recipes = TenDuplicateRecipes.recipes
        assert(recipes.size == 10)
        var cachedRecipes = cache.getAllRecipes()
        assert(cachedRecipes.isEmpty())
        cache.insertRecipes(recipes.map { it.toFavoriteEntity() })
        cachedRecipes = cache.getAllRecipes()
        assert(cachedRecipes.size == 10)

        deleteMultipleRecipesFromFavorite.execute(ThreeDuplicateRecipes.recipes).toList()

        //confirm 3 recipes were deleted
        cachedRecipes = cache.getAllRecipes()
        assert(cachedRecipes.size == 7)

    }

}