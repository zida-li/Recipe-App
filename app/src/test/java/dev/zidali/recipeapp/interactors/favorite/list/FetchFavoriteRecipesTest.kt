package dev.zidali.recipeapp.interactors.favorite.list

import dev.zidali.recipeapp.business.datasource.cache.main.FavoriteRecipeDao
import dev.zidali.recipeapp.business.datasource.cache.main.toFavoriteEntity
import dev.zidali.recipeapp.business.domain.models.Recipe
import dev.zidali.recipeapp.business.interactors.main.favorite.list.FetchFavoriteRecipes
import dev.zidali.recipeapp.datasource.cache.AppDatabaseFake
import dev.zidali.recipeapp.datasource.cache.FavoriteRecipeDaoFake
import dev.zidali.recipeapp.datasource.testfakes.TenDuplicateRecipes
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FetchFavoriteRecipesTest {

    private val appDataBase = AppDatabaseFake()

    //system in test
    private lateinit var fetchFavoriteRecipes: FetchFavoriteRecipes

    //dependencies
    private lateinit var cache: FavoriteRecipeDao

    @BeforeEach
    fun setup() {

        cache = FavoriteRecipeDaoFake(appDataBase)

        fetchFavoriteRecipes = FetchFavoriteRecipes(
            favoriteRecipeDao = cache
        )
    }

    @Test
    fun success_FetchingRecipe() = runBlocking {

        //verify db is empty, insert 10 recipes, then confirm 10 was inserted
        val recipes = TenDuplicateRecipes.recipes
        assert(recipes.size == 10)
        var cachedRecipes = cache.getAllRecipes()
        assert(cachedRecipes.isEmpty())
        cache.insertRecipes(recipes.map { it.toFavoriteEntity() })
        cachedRecipes = cache.getAllRecipes()
        assert(cachedRecipes.size == 10)

        val emissions = fetchFavoriteRecipes.execute().toList()

        assert(emissions[0].data?.size == 10)
        assert(emissions[0].data?.get(0) != null)
        assert(emissions[0].data?.get(0) is Recipe)

    }

}