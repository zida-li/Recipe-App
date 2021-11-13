package dev.zidali.recipeapp.interactors.shoppinglist

import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListDao
import dev.zidali.recipeapp.business.interactors.main.shoppinglist.UpdateRecipeState
import dev.zidali.recipeapp.datasource.cache.AppDatabaseFake
import dev.zidali.recipeapp.datasource.cache.ShoppingListDaoFake
import dev.zidali.recipeapp.datasource.testfakes.SingleDuplicateRecipe
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UpdateRecipeStateTest {

    private val appDataBase = AppDatabaseFake()

    //system in test
    private lateinit var updateRecipeState: UpdateRecipeState

    //dependencies
    private lateinit var shoppingListDao: ShoppingListDao

    @BeforeEach
    fun setup(){

        shoppingListDao = ShoppingListDaoFake(appDataBase)

        updateRecipeState = UpdateRecipeState(
            shoppingListDao
        )

    }

    @Test
    fun success() = runBlocking {

        assert(appDataBase.shoppingList.isEmpty())

        updateRecipeState.execute(
            SingleDuplicateRecipe.recipe
        ).toList()

        assert(appDataBase.shoppingList.isNotEmpty())

    }

}