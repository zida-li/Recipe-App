package dev.zidali.recipeapp.interactors.shared

import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListDao
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.toShoppingListEntity
import dev.zidali.recipeapp.business.interactors.main.shared.CompareToShoppingList
import dev.zidali.recipeapp.datasource.cache.AppDatabaseFake
import dev.zidali.recipeapp.datasource.cache.ShoppingListDaoFake
import dev.zidali.recipeapp.datasource.testfakes.TenDuplicateRecipes
import dev.zidali.recipeapp.datasource.testfakes.SingleDuplicateRecipe
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CompareToShoppingListTest {

    private val appDataBase = AppDatabaseFake()

    //system in test
    private lateinit var compareToShoppingList: CompareToShoppingList

    //dependencies
    private lateinit var shoppingListDao: ShoppingListDao

    @BeforeEach
    fun setup() {

        shoppingListDao = ShoppingListDaoFake(appDataBase)

        compareToShoppingList = CompareToShoppingList(
            shoppingListDao
        )
    }

    @Test
    fun success_1Recipe() = runBlocking {

        //make sure cache is empty
        val recipes = TenDuplicateRecipes.recipes
        val cachedRecipe = shoppingListDao.getAllRecipes()
        assert(cachedRecipe.isEmpty())
        shoppingListDao.insertRecipes(recipes.map { it.toShoppingListEntity() })
        assert(cachedRecipe.size == 10)

        val emissions = compareToShoppingList.execute(
            SingleDuplicateRecipe.recipe
        ).toList()

        assert(emissions[0].data?.isInShoppingList!!)
    }

}