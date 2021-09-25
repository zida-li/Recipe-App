package com.example.recipeappfivelearning.business.datasource.network.main.responses

import com.example.recipeappfivelearning.business.datasource.network.main.SearchDto
import com.google.gson.annotations.SerializedName

data class SearchResponse (

    @SerializedName("count")
    var count: Int,

    @SerializedName("hits")
    var recipeHits: MutableList<SearchDto>,

    @SerializedName("more")
    var moreResultAvailable: Boolean? = null,

)