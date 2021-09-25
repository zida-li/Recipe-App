package com.example.recipeappfivelearning.business.domain.util

class Converters {

    companion object {

        fun convertIngredientsToList(ingredientsString: String?):
                List<String>{
            val list: ArrayList<String> = ArrayList()
            ingredientsString?.let {
                for(ingredient in it.split(",")){
                    list.add(ingredient)
                }
            }
            return list
        }

        fun convertIngredientListToString(ingredients: List<String>):
                String{
            val ingredientsString = StringBuilder()
            for(ingredient in ingredients) {
                ingredientsString.append("$ingredient,")
            }
            return ingredientsString.toString()
        }

    }

}