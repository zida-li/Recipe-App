package dev.zidali.recipeapp.interactors.shoppinglist

import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.relations.RecipeWithIngredientDao
import dev.zidali.recipeapp.business.interactors.main.shoppinglist.FetchShoppingListRecipes
import dev.zidali.recipeapp.datasource.cache.AppDatabaseFake
import dev.zidali.recipeapp.datasource.cache.RecipeWithIngredientDaoFake
import dev.zidali.recipeapp.datasource.testfakes.OneRecipeWithIngredient
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FetchShoppingListRecipesTest {

    private val appDataBase = AppDatabaseFake()

    //system in test
    private lateinit var fetchShoppingListRecipes: FetchShoppingListRecipes

    //dependencies
    private lateinit var recipeWithIngredientDao: RecipeWithIngredientDao

    @BeforeEach
    fun setup() {

        recipeWithIngredientDao = RecipeWithIngredientDaoFake(appDataBase)

        fetchShoppingListRecipes = FetchShoppingListRecipes(
            recipeWithIngredientDao
        )
    }

    @Test
    fun test_success1Recipe() = runBlocking {

        //verify cache is empty
        val cacheRecipeWithIngredient = recipeWithIngredientDao.getRecipeWithIngredient()
        assert(cacheRecipeWithIngredient.isEmpty())

        appDataBase.recipeWithIngredient.add(OneRecipeWithIngredient.recipeWithIngredient)
        assert(cacheRecipeWithIngredient.isNotEmpty())

        val emissions = fetchShoppingListRecipes.execute().toList()

        assert(emissions[0].data?.size == 1)

    }

}