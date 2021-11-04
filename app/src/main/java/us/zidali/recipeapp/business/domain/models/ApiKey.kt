package us.zidali.recipeapp.business.domain.models

data class ApiKey (
    var app_id: String = "",
    var app_key: String = "",
) {
    class ApiKeyError {

        companion object{

            fun mustFillAllFields(): String{
                return "Please fill both fields."
            }

            fun none():String{
                return "None"
            }

        }
    }

    fun isValid(): String{

        if(app_id.isEmpty()
            || app_key.isEmpty()){

            return ApiKeyError.mustFillAllFields()
        }
        return ApiKeyError.none()
    }

}