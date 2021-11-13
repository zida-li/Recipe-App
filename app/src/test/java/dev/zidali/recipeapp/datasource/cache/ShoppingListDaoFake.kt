package dev.zidali.recipeapp.datasource.cache

import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListDao
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListEntity

class ShoppingListDaoFake (
    private val db: AppDatabaseFake
): ShoppingListDao {

    override suspend fun insertRecipe(recipe: ShoppingListEntity): Long {
        db.shoppingList.removeIf {
            it.recipeId == recipe.recipeId
        }
        db.shoppingList.add(recipe)
        return 1
    }

    override suspend fun insertRecipes(recipes: List<ShoppingListEntity>): LongArray {
        val longArray = longArrayOf(recipes.size.toLong())
        for (recipe in recipes) {
            db.shoppingList.removeIf {
                it.recipeName == recipe.recipeName
            }
            db.shoppingList.add(recipe)
        }
        longArray.all {
            true
        }
        return longArray
    }

    override suspend fun getAllRecipes(): MutableList<ShoppingListEntity> {
        return db.shoppingList
    }

    override suspend fun deleteRecipe(recipe: ShoppingListEntity) {
        db.shoppingList.remove(recipe)
    }

}