package dev.zidali.recipeapp.interactors.favorite.detail

import dev.zidali.recipeapp.business.datasource.cache.main.FavoriteRecipeDao
import dev.zidali.recipeapp.business.datasource.cache.main.toFavoriteEntity
import dev.zidali.recipeapp.business.interactors.main.favorite.detail.FetchFavoriteRecipe
import dev.zidali.recipeapp.datasource.cache.AppDatabaseFake
import dev.zidali.recipeapp.datasource.cache.FavoriteRecipeDaoFake
import dev.zidali.recipeapp.datasource.testfakes.SingleDuplicateRecipe
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FetchFavoriteRecipeTest {

    private val appDataBase = AppDatabaseFake()

    //system in test
    private lateinit var fetchFavoriteRecipe: FetchFavoriteRecipe

    //dependencies
    private lateinit var cache: FavoriteRecipeDao

    @BeforeEach
    fun setup() {

        cache = FavoriteRecipeDaoFake(appDataBase)

        fetchFavoriteRecipe = FetchFavoriteRecipe(
            favoriteRecipeDao = cache
        )

    }

    @Test
    fun test_favoriteRecipeFetched() = runBlocking {

        //Insert recipe into database
        val recipe = SingleDuplicateRecipe.recipe
        var cachedRecipe = cache.getAllRecipes()
        assert(cachedRecipe.isEmpty())
        cache.insertRecipe(recipe.toFavoriteEntity())
        cachedRecipe = cache.getAllRecipes()
        assert(cachedRecipe.size == 1)

        val emissions = fetchFavoriteRecipe.execute(
            SingleDuplicateRecipe.recipeName
        ).toList()

        assert(emissions[0].data?.recipeName == SingleDuplicateRecipe.recipeName)

    }

}