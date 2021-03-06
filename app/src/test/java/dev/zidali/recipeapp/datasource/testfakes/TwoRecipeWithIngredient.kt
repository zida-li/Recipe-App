package dev.zidali.recipeapp.datasource.testfakes

import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListEntity
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.ShoppingListIngredientEntity
import dev.zidali.recipeapp.business.datasource.cache.main.shoppinglist.relations.RecipeWithIngredient

object TwoRecipeWithIngredient {

    val chickenVesuvio = "Chicken Vesuvio"
    val chickenPaprikash = "Chicken Paprikash"

    val recipesWithIngredient = listOf<RecipeWithIngredient>(
            RecipeWithIngredient(
            recipe = ShoppingListEntity(
                recipeName = chickenVesuvio,
                recipeImageUrl = "http://www.seriouseats.com/recipes/2011/12/chicken-vesuvio-recipe.html",
                recipeIngredients = "1/2 cup olive oil@#5 cloves garlic, peeled@#2 large russet potatoes, peeled and cut into chunks@#1 34 pound chicken, cut into 8 pieces (or 3 pound chicken legs)@#3/4 cup white wine@#3/4 cup chicken stock@#3 tablespoons chopped parsley@#1 tablespoon dried oregano@#Salt and pepper@#1 cup frozen peas, thawed@#@#",
                recipeId = "http://www.edamam.com/ontologies/edamam.owl#recipe_b79327d05b8e5b838ad6cfd9576b30b6",
                recipeCalories = 4228.043058200812.toFloat(),
                timeSaved = null,
                isExpanded = false,
                isInShoppingList = false,
                isMultiSelectionModeEnabled = false,
            ),
            ingredients = mutableListOf<ShoppingListIngredientEntity>(
                ShoppingListIngredientEntity(
                    recipeIngredient = "1/2 cup olive oil",
                    recipeName = chickenVesuvio,
                    isChecked = false,
                ),
                ShoppingListIngredientEntity(
                    recipeIngredient = "5 cloves garlic, peeled",
                    recipeName = chickenVesuvio,
                    isChecked = false,
                ),
                ShoppingListIngredientEntity(
                    recipeIngredient = "2 large russet potatoes, peeled and cut into chunks",
                    recipeName = chickenVesuvio,
                    isChecked = false,
                ),
                ShoppingListIngredientEntity(
                    recipeIngredient = "1 3-4 pound chicken, cut into 8 pieces (or 3 pound chicken legs)",
                    recipeName = chickenVesuvio,
                    isChecked = false,
                ),
                ShoppingListIngredientEntity(
                    recipeIngredient = "3/4 cup white wine",
                    recipeName = chickenVesuvio,
                    isChecked = false,
                ),
                ShoppingListIngredientEntity(
                    recipeIngredient = "3/4 cup chicken stock",
                    recipeName = chickenVesuvio,
                    isChecked = false,
                ),
                ShoppingListIngredientEntity(
                    recipeIngredient = "3 tablespoons chopped parsley",
                    recipeName = chickenVesuvio,
                    isChecked = false,
                ),
                ShoppingListIngredientEntity(
                    recipeIngredient = "1 tablespoon dried oregano",
                    recipeName = chickenVesuvio,
                    isChecked = false,
                ),
                ShoppingListIngredientEntity(
                    recipeIngredient = "Salt and pepper",
                    recipeName = chickenVesuvio,
                    isChecked = false,
                ),
                ShoppingListIngredientEntity(
                    recipeIngredient = "1 cup frozen peas, thawed",
                    recipeName = chickenVesuvio,
                    isChecked = false,
                ),
            ),
        ),
        RecipeWithIngredient(
            recipe = ShoppingListEntity(
                recipeName = chickenPaprikash,
                recipeImageUrl = "https://www.edamam.com/web-img/e12/e12b8c5581226d7639168f41d126f2ff.jpg",
                recipeIngredients = "640 grams chicken  drumsticks and thighs ( 3 whole chicken legs cut apart)@#1/2 teaspoon salt@#1/4 teaspoon black pepper@#1 tablespoon butter ??? cultured unsalted (or olive oil)@#240 grams onion sliced thin (1 large onion)@#70 grams Anaheim pepper chopped (1 large pepper)@#25 grams paprika (about 1/4 cup)@#1 cup chicken stock@#1/2 cup sour cream@#1 tablespoon flour ??? allpurpose@#@#@#",
                recipeId = "http://www.edamam.com/ontologies/edamam.owl#recipe_8275bb28647abcedef0baaf2dcf34f8b",
                recipeCalories = 3033.2012500008163.toFloat(),
                timeSaved = null,
                isExpanded = false,
                isInShoppingList = false,
                isMultiSelectionModeEnabled = false,
            ),
            ingredients = mutableListOf<ShoppingListIngredientEntity>(
                ShoppingListIngredientEntity(
                    recipeIngredient = "640 grams chicken - drumsticks and thighs ( 3 whole chicken legs cut apart)",
                    recipeName = chickenPaprikash,
                    isChecked = false,
                ),
                ShoppingListIngredientEntity(
                    recipeIngredient = "1/2 teaspoon salt",
                    recipeName = chickenPaprikash,
                    isChecked = false,
                ),
                ShoppingListIngredientEntity(
                    recipeIngredient = "1/4 teaspoon black pepper",
                    recipeName = chickenPaprikash,
                    isChecked = false,
                ),
                ShoppingListIngredientEntity(
                    recipeIngredient = "1 tablespoon butter ??? cultured unsalted (or olive oil)",
                    recipeName = chickenPaprikash,
                    isChecked = false,
                ),
                ShoppingListIngredientEntity(
                    recipeIngredient = "240 grams onion sliced thin (1 large onion)",
                    recipeName = chickenPaprikash,
                    isChecked = false,
                ),
                ShoppingListIngredientEntity(
                    recipeIngredient = "70 grams Anaheim pepper chopped (1 large pepper)",
                    recipeName = chickenPaprikash,
                    isChecked = false,
                ),
                ShoppingListIngredientEntity(
                    recipeIngredient = "25 grams paprika (about 1/4 cup)",
                    recipeName = chickenPaprikash,
                    isChecked = false,
                ),
                ShoppingListIngredientEntity(
                    recipeIngredient = "1 cup chicken stock",
                    recipeName = chickenPaprikash,
                    isChecked = false,
                ),
                ShoppingListIngredientEntity(
                    recipeIngredient = "1/2 cup sour cream",
                    recipeName = chickenPaprikash,
                    isChecked = false,
                ),
                ShoppingListIngredientEntity(
                    recipeIngredient = "1 tablespoon flour ??? all-purpose",
                    recipeName = chickenPaprikash,
                    isChecked = false,
                ),
            ),
        )
    )

}