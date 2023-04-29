package com.ghadeer.articlesviewer.network.responses

import com.google.gson.annotations.SerializedName

data class ArticlesResponse(
    @SerializedName("status")       val status: String? = null,
    @SerializedName("copyright")    val copyright: String? = null,
    @SerializedName("num_results")  val numResults: Int? = null,
    @SerializedName("results")      val results: List<ArticleResponse> = emptyList(),
)