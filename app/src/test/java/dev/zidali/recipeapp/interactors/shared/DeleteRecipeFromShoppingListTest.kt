package dev.zidali.recipeapp.interactors.shared

import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListDao
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.toShoppingListEntity
import dev.zidali.recipeapp.business.interactors.main.shared.DeleteRecipeFromShoppingList
import dev.zidali.recipeapp.datasource.cache.AppDatabaseFake
import dev.zidali.recipeapp.datasource.cache.ShoppingListDaoFake
import dev.zidali.recipeapp.datasource.testfakes.TenDuplicateRecipes
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeleteRecipeFromShoppingListTest {

    private val appDataBase = AppDatabaseFake()

    //system in test
    private lateinit var deleteRecipeFromShoppingList: DeleteRecipeFromShoppingList

    //dependencies
    private lateinit var shoppingListDao: ShoppingListDao

    @BeforeEach
    fun setup() {

        shoppingListDao = ShoppingListDaoFake(appDataBase)

        deleteRecipeFromShoppingList = DeleteRecipeFromShoppingList(
            shoppingListDao
        )

    }

    @Test
    fun success_1Recipe() = runBlocking {

        val recipes = TenDuplicateRecipes.recipes
        val cachedRecipe = shoppingListDao.getAllRecipes()
        assert(cachedRecipe.isEmpty())
        shoppingListDao.insertRecipes(recipes.map { it.toShoppingListEntity() })
        assert(cachedRecipe.size == 10)

        deleteRecipeFromShoppingList.execute(
            recipes[0]
        ).toList()

        assert(cachedRecipe.size == 9)

    }

}