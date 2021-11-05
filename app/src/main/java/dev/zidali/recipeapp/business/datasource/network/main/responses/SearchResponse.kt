package dev.zidali.recipeapp.business.datasource.network.main.responses

import com.google.gson.annotations.SerializedName
import dev.zidali.recipeapp.business.datasource.network.main.SearchDto

data class SearchResponse (

    @SerializedName("count")
    var count: Int,

    @SerializedName("hits")
    var recipeHits: MutableList<SearchDto>,

    @SerializedName("more")
    var moreResultAvailable: Boolean? = null,

    )