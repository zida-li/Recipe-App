package dev.zidali.recipeapp.interactors.shoppinglist

import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListIngredientDao
import dev.zidali.recipeapp.business.interactors.main.shoppinglist.SetIsCheckedIngredient
import dev.zidali.recipeapp.datasource.cache.AppDatabaseFake
import dev.zidali.recipeapp.datasource.cache.ShoppingListIngredientDaoFake
import dev.zidali.recipeapp.datasource.testfakes.OneIngredient
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SetIsCheckedIngredientTest {

    private val appDataBase = AppDatabaseFake()

    //system in test
    private lateinit var setisCheckedIngredient: SetIsCheckedIngredient

    //dependencies
    private lateinit var shoppingListIngredientDao: ShoppingListIngredientDao

    @BeforeEach
    fun setup() {

        shoppingListIngredientDao = ShoppingListIngredientDaoFake(appDataBase)

        setisCheckedIngredient = SetIsCheckedIngredient(
            shoppingListIngredientDao
        )

    }

    @Test
    fun success() = runBlocking {

        assert(appDataBase.shoppingListIngredient.isEmpty())

        setisCheckedIngredient.execute(
            OneIngredient.ingredient
        ).toList()

        assert(appDataBase.shoppingListIngredient.isNotEmpty())
    }

}