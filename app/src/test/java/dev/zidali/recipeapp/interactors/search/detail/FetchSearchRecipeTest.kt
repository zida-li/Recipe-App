package dev.zidali.recipeapp.interactors.search.detail

import dev.zidali.recipeapp.business.datasource.cache.main.search.TemporaryRecipeDao
import dev.zidali.recipeapp.business.datasource.cache.main.search.toTemporaryEntity
import dev.zidali.recipeapp.business.interactors.main.search.detail.FetchSearchRecipe
import dev.zidali.recipeapp.datasource.cache.AppDatabaseFake
import dev.zidali.recipeapp.datasource.cache.TemporaryRecipeDaoFake
import dev.zidali.recipeapp.datasource.testfakes.SingleDuplicateRecipe
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FetchSearchRecipeTest {

    private val appDataBase = AppDatabaseFake()

    //system in test
    private lateinit var fetchSearchRecipe: FetchSearchRecipe

    //dependencies
    private lateinit var cache: TemporaryRecipeDao

    @BeforeEach
    fun setup() {

        cache = TemporaryRecipeDaoFake(appDataBase)

        fetchSearchRecipe = FetchSearchRecipe(
            temporaryRecipeDao = cache
        )

    }

    @Test
    fun test_recipeFetched() = runBlocking {

        //Insert recipe into database
        val recipe = SingleDuplicateRecipe.recipe
        var cachedRecipe = cache.getAllRecipes()
        assert(cachedRecipe.isEmpty())
        cache.insertRecipe(recipe.toTemporaryEntity())
        cachedRecipe = cache.getAllRecipes()
        assert(cachedRecipe.size == 1)

        val emissions = fetchSearchRecipe.execute(
            recipeName = recipe.recipeName!!
        ).toList()

        //Confirm recipe is deleted
        val cacheRecipe = cache.getAllRecipes()
        assert(cacheRecipe.isEmpty())

        assert(emissions[0].data?.recipe != null)

    }

}