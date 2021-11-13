package dev.zidali.recipeapp.datasource.cache

import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListIngredientDao
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListIngredientEntity

class ShoppingListIngredientDaoFake(
    private val db: AppDatabaseFake
): ShoppingListIngredientDao {

    override suspend fun insertIngredient(recipe: ShoppingListIngredientEntity): Long {
        db.shoppingListIngredient.removeIf {
            it.recipeIngredient == recipe.recipeIngredient
        }
        db.shoppingListIngredient.add(recipe)
        return 1
    }

    override suspend fun insertIngredients(recipes: List<ShoppingListIngredientEntity>): LongArray {
        val longArray = longArrayOf(recipes.size.toLong())
        for (recipe in recipes) {
            db.shoppingListIngredient.removeIf {
                it.recipeIngredient == recipe.recipeIngredient
            }
            db.shoppingListIngredient.add(recipe)
        }
        longArray.all {
            true
        }
        return longArray
    }

    override suspend fun getAllIngredients(): MutableList<ShoppingListIngredientEntity> {
        return db.shoppingListIngredient
    }

    override suspend fun deleteRecipe(recipe: ShoppingListIngredientEntity) {
        db.shoppingListIngredient.remove(recipe)
    }


}