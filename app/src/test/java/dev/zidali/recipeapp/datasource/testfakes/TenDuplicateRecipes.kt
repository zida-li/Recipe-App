package dev.zidali.recipeapp.datasource.testfakes

import dev.zidali.recipeapp.business.domain.models.Recipe

object TenDuplicateRecipes {

    //fake values
    val recipeImageUrl = ""
    val recipeId = ""
    val recipeCalories = 1
    val timeSaved = null
    val isExpanded = false
    val isFavorite = false
    val isInShoppingList = false

    val recipes = listOf<Recipe>(
        Recipe(
            recipeName = "Chicken Vesuvio",
            recipeImageUrl = "http://www.seriouseats.com/recipes/2011/12/chicken-vesuvio-recipe.html",
            recipeIngredients = listOf(
                "1/2 cup olive oil",
                "5 cloves garlic, peeled",
                "2 large russet potatoes, peeled and cut into chunks",
                "1 3-4 pound chicken, cut into 8 pieces (or 3 pound chicken legs)",
                "3/4 cup white wine",
                "3/4 cup chicken stock",
                "3 tablespoons chopped parsley",
                "1 tablespoon dried oregano",
                "Salt and pepper",
                "1 cup frozen peas, thawed"
            ),
            recipeId = "http://www.edamam.com/ontologies/edamam.owl#recipe_b79327d05b8e5b838ad6cfd9576b30b6",
            recipeCalories = 4228.043058200812.toFloat(),
            timeSaved = timeSaved,
            isExpanded = isExpanded,
            isFavorite = isFavorite,
            isInShoppingList = isInShoppingList,
            isMultiSelectionModeEnabled = false,
        ),
        Recipe(
            recipeName = "Chicken Paprikash",
            recipeImageUrl = "https://www.edamam.com/web-img/e12/e12b8c5581226d7639168f41d126f2ff.jpg",
            recipeIngredients = listOf(
                "640 grams chicken - drumsticks and thighs ( 3 whole chicken legs cut apart)",
                "1/2 teaspoon salt",
                "1/4 teaspoon black pepper",
                "1 tablespoon butter – cultured unsalted (or olive oil)",
                "240 grams onion sliced thin (1 large onion)",
                "70 grams Anaheim pepper chopped (1 large pepper)",
                "25 grams paprika (about 1/4 cup)",
                "1 cup chicken stock",
                "1/2 cup sour cream",
                "1 tablespoon flour – all-purpose"
            ),
            recipeId = "http://www.edamam.com/ontologies/edamam.owl#recipe_8275bb28647abcedef0baaf2dcf34f8b",
            recipeCalories = 3033.2012500008163.toFloat(),
            timeSaved = timeSaved,
            isExpanded = isExpanded,
            isFavorite = isFavorite,
            isInShoppingList = isInShoppingList,
            isMultiSelectionModeEnabled = false,
        ),
        Recipe(
            recipeName = "Baked Chicken",
            recipeImageUrl = recipeImageUrl,
            recipeIngredients = listOf(
                "6 bone-in chicken breast halves, or 6 chicken thighs and wings, skin-on",
                "1/2 teaspoon coarse salt",
                "1/2 teaspoon Mrs. Dash seasoning",
                "1/4 teaspoon freshly ground black pepper"
            ),
            recipeId = recipeId,
            recipeCalories = recipeCalories.toFloat(),
            timeSaved = timeSaved,
            isExpanded = isExpanded,
            isFavorite = isFavorite,
            isInShoppingList = isInShoppingList,
        ),
        Recipe(
            recipeName = "Catalan Chicken",
            recipeImageUrl = recipeImageUrl,
            recipeIngredients = listOf(
                "1 whole 4-pound chicken, quartered",
                "8 slices bacon",
                "30 cloves garlic",
                "3 lemons, peeled, rinds thinly sliced and reserved",
                "½ cup Banyuls or another fortified dessert wine",
                "1 cup veal or chicken stock"
            ),
            recipeId = recipeId,
            recipeCalories = recipeCalories.toFloat(),
            timeSaved = timeSaved,
            isExpanded = isExpanded,
            isFavorite = isFavorite,
            isInShoppingList = isInShoppingList,
        ),
        Recipe(
            recipeName = "Persian Chicken",
            recipeImageUrl = recipeImageUrl,
            recipeIngredients = listOf(
                "2 large onions",
                "750 g chicken",
                "500 g mushrooms",
                "1 cup water",
                "1 cup red wine",
                "2 teaspoons chicken stock",
                "200 ml mayonnaise",
                "200 ml cream",
                "small bunch of parsley",
                "1 teaspoon curry powder"
            ),
            recipeId = recipeId,
            recipeCalories = recipeCalories.toFloat(),
            timeSaved = timeSaved,
            isExpanded = isExpanded,
            isFavorite = isFavorite,
            isInShoppingList = isInShoppingList,
        ),
        Recipe(
            recipeName = "Chicken Stew",
            recipeImageUrl = recipeImageUrl,
            recipeIngredients = listOf(
                "1 pound chicken cut in pieces",
                "4 carrots",
                "1 onion",
                "1 leek",
                "1 green pepper",
                "kosher salt",
                "Freshly ground black pepper",
                "Extra Virgin Olive Oil",
                "1 cup white wine",
                "Chicken broth"
            ),
            recipeId = recipeId,
            recipeCalories = recipeCalories.toFloat(),
            timeSaved = timeSaved,
            isExpanded = isExpanded,
            isFavorite = isFavorite,
            isInShoppingList = isInShoppingList,
        ),
        Recipe(
            recipeName = "Roast Chicken",
            recipeImageUrl = recipeImageUrl,
            recipeIngredients = listOf(
                "1 whole chicken, about 3-4 pounds",
                "-- Salt and fresh-ground pepper, to taste",
                "3 to 4 sprigs thyme, or other herbs",
                "-- Olive oil, to taste",
                "-- Chicken stock (optional)"
            ),
            recipeId = recipeId,
            recipeCalories = recipeCalories.toFloat(),
            timeSaved = timeSaved,
            isExpanded = isExpanded,
            isFavorite = isFavorite,
            isInShoppingList = isInShoppingList,
        ),
        Recipe(
            recipeName = "Chicken Liver Pâté",
            recipeImageUrl = recipeImageUrl,
            recipeIngredients = listOf(
                "8 oz. chicken livers, cleaned",
                "4 cups chicken stock",
                "2 tbsp. rendered chicken fat or unsalted butter",
                "½ medium yellow onion, minced",
                "1½ tbsp. cognac or brandy",
                "2 hard-boiled eggs",
                "Kosher salt and freshly ground black pepper, to taste",
                "Toast points, for serving"
            ),
            recipeId = recipeId,
            recipeCalories = recipeCalories.toFloat(),
            timeSaved = timeSaved,
            isExpanded = isExpanded,
            isFavorite = isFavorite,
            isInShoppingList = isInShoppingList,
        ),
        Recipe(
            recipeName = "Kreplach (Chicken Dumplings)",
            recipeImageUrl = recipeImageUrl,
            recipeIngredients = listOf(
                "1½ teaspoons canola oil",
                "½ small shallot, finely chopped",
                "1 cup (about ½ pound) raw, boneless chicken meat (preferably from 3 boneless chicken thighs), roughly chopped",
                "⅔ cup (about ¼ pound) chicken skin and fat (reserved from the 3 chicken thighs)",
                "2 chicken livers (optional)",
                "2 garlic cloves, finely chopped",
                "¼ cup finely chopped chives, plus extra for serving",
                "1¼ teaspoons kosher salt",
                "¾ teaspoon freshly ground black pepper",
                "30 to 34 square wonton wrappers",
                "8 cups store-bought or homemade chicken broth"
            ),
            recipeId = recipeId,
            recipeCalories = recipeCalories.toFloat(),
            timeSaved = timeSaved,
            isExpanded = isExpanded,
            isFavorite = isFavorite,
            isInShoppingList = isInShoppingList,
        ),
        Recipe(
            recipeName = "Chicken cacciatore",
            recipeImageUrl = recipeImageUrl,
            recipeIngredients = listOf(
                "8 tbsp olive oil",
                "1 onion, sliced",
                "2 celery stalks, roughly chopped",
                "2 medium carrots, roughly chopped",
                "6 chicken breasts, or chicken thighs, bones removed",
                "175ml/6fl oz white wine",
                "3 tbsp tomato purée",
                "500ml/17 fl oz chicken stock",
                "2 bay leaves",
                "2-3 sage leaves",
                "1 rosemary sprig",
                "250g/9oz easy-cook polenta",
                "Knob of butter",
                "25g/1oz parmesan"
            ),
            recipeId = recipeId,
            recipeCalories = recipeCalories.toFloat(),
            timeSaved = timeSaved,
            isExpanded = isExpanded,
            isFavorite = isFavorite,
            isInShoppingList = isInShoppingList,
        )
    )
}