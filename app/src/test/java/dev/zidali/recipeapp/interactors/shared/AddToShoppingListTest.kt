package dev.zidali.recipeapp.interactors.shared

import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListDao
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListIngredientDao
import dev.zidali.recipeapp.business.interactors.main.shared.AddToShoppingList
import dev.zidali.recipeapp.datasource.cache.AppDatabaseFake
import dev.zidali.recipeapp.datasource.cache.ShoppingListDaoFake
import dev.zidali.recipeapp.datasource.cache.ShoppingListIngredientDaoFake
import dev.zidali.recipeapp.datasource.testfakes.SingleDuplicateRecipe
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AddToShoppingListTest {

    private val appDataBase = AppDatabaseFake()

    //system in test
    private lateinit var addToShoppingList: AddToShoppingList

    //dependencies
    private lateinit var shoppingListDao: ShoppingListDao
    private lateinit var shoppingListIngredientDao: ShoppingListIngredientDao

    @BeforeEach
    fun setup() {

        shoppingListDao = ShoppingListDaoFake(appDataBase)
        shoppingListIngredientDao = ShoppingListIngredientDaoFake(appDataBase)

        addToShoppingList = AddToShoppingList(
            shoppingListDao,
            shoppingListIngredientDao,
        )

    }

    @Test
    fun test_success1Recipe() = runBlocking {

        //verify cache is empty
        var cacheShoppingList = shoppingListDao.getAllRecipes()
        var cacheShoppingListIngredient = shoppingListIngredientDao.getAllIngredients()
        assert(cacheShoppingList.isEmpty())
        assert(cacheShoppingListIngredient.isEmpty())

        addToShoppingList.execute(
            SingleDuplicateRecipe.recipe
        ).toList()

        cacheShoppingList = shoppingListDao.getAllRecipes()
        cacheShoppingListIngredient = shoppingListIngredientDao.getAllIngredients()
        assert(cacheShoppingList.isNotEmpty())
        assert(cacheShoppingListIngredient.isNotEmpty())

    }

}