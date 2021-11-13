package dev.zidali.recipeapp.interactors.shoppinglist

import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListDao
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListIngredientDao
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.relations.RecipeWithIngredientDao
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.toShoppingListEntity
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.toShoppingListIngredientEntity
import dev.zidali.recipeapp.business.interactors.main.shared.AddToShoppingList
import dev.zidali.recipeapp.business.interactors.main.shared.DeleteRecipeFromShoppingList
import dev.zidali.recipeapp.business.interactors.main.shoppinglist.DeleteMultipleRecipesFromShoppingList
import dev.zidali.recipeapp.business.interactors.main.shoppinglist.FetchShoppingListRecipes
import dev.zidali.recipeapp.datasource.cache.AppDatabaseFake
import dev.zidali.recipeapp.datasource.cache.RecipeWithIngredientDaoFake
import dev.zidali.recipeapp.datasource.cache.ShoppingListDaoFake
import dev.zidali.recipeapp.datasource.cache.ShoppingListIngredientDaoFake
import dev.zidali.recipeapp.datasource.testfakes.TenDuplicateRecipes
import dev.zidali.recipeapp.datasource.testfakes.TwentyIngredients
import dev.zidali.recipeapp.datasource.testfakes.TwoRecipeWithIngredient
import dev.zidali.recipeapp.datasource.testfakes.TwoRecipeWithIngredientCheck
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeleteMultipleRecipesFromShoppingListTest {

    private val appDataBase = AppDatabaseFake()

    //system in test
    private lateinit var deleteMultipleRecipesFromShoppingList: DeleteMultipleRecipesFromShoppingList

    //dependencies
    private lateinit var shoppingListDao: ShoppingListDao
    private lateinit var shoppingListIngredientDao: ShoppingListIngredientDao

    @BeforeEach
    fun setup() {

        shoppingListDao = ShoppingListDaoFake(appDataBase)

        shoppingListIngredientDao = ShoppingListIngredientDaoFake(appDataBase)

        deleteMultipleRecipesFromShoppingList = DeleteMultipleRecipesFromShoppingList(
            shoppingListDao,
            shoppingListIngredientDao,
        )

    }

    @Test
    fun success_2Recipe() = runBlocking {

        assert(appDataBase.shoppingList.isEmpty())
        shoppingListDao.insertRecipes(
            TwoRecipeWithIngredientCheck.recipe.map { it.toShoppingListEntity()
            })
        assert(appDataBase.shoppingList.size == 2)


        assert(appDataBase.shoppingListIngredient.isEmpty())
        shoppingListIngredientDao.insertIngredients(
            TwentyIngredients.ingredients.map { it.toShoppingListIngredientEntity()
            })
        assert(appDataBase.shoppingListIngredient.size == 20)

        deleteMultipleRecipesFromShoppingList.execute(
            TwoRecipeWithIngredientCheck.recipe
        ).toList()

        assert(appDataBase.shoppingList.isEmpty())
        assert(appDataBase.shoppingListIngredient.isEmpty())
    }

}