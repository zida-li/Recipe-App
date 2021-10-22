package com.example.recipeappfivelearning.business.domain.util

import android.util.Log

class Converters {

    companion object {

        fun convertIngredientsToList(ingredientsString: String?):
                List<String>{
            val list: ArrayList<String> = ArrayList()
            ingredientsString?.let {
                for(ingredient in it.split("@#")){
                    val regex = Regex("[\\\\@#\"-]")
                    val result = regex.replace(ingredient, "")
                    list.add(result)
                }
            }
            return list
        }

        fun convertIngredientListToString(ingredients: List<String>):
                String{
            val ingredientsString = StringBuilder()
            for(ingredient in ingredients) {
                ingredientsString.append("$ingredient@#")
            }
            return ingredientsString.toString()
        }

    }

}