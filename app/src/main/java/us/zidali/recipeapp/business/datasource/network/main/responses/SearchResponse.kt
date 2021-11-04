package us.zidali.recipeapp.business.datasource.network.main.responses

import com.google.gson.annotations.SerializedName
import us.zidali.recipeapp.business.datasource.network.main.SearchDto

data class SearchResponse (

    @SerializedName("count")
    var count: Int,

    @SerializedName("hits")
    var recipeHits: MutableList<SearchDto>,

    @SerializedName("more")
    var moreResultAvailable: Boolean? = null,

    )