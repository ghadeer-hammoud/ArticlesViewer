package com.ghadeer.articlesviewer.network.responses

import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    @SerializedName("uri")              val uri: String? = null,
    @SerializedName("url")              val url: String? = null,
    @SerializedName("id")               val id: Long? = null,
    @SerializedName("asset_id")         val assetId: Long? = null,
    @SerializedName("source")           val source: String? = null,
    @SerializedName("published_date")   val publishedDate: String? = null,
    @SerializedName("updated")          val updated: String? = null,
    @SerializedName("section")          val section: String? = null,
    @SerializedName("subsection")       val subsection: String? = null,
    @SerializedName("nytdsection")      val nytdSection: String? = null,
    @SerializedName("adx_keywords")     val adxKeywords: String? = null,
    @SerializedName("column")           val column: String? = null,
    @SerializedName("byline")           val byline: String? = null,
    @SerializedName("type")             val type: String? = null,
    @SerializedName("title")            val title: String? = null,
    @SerializedName("abstract")         val abstract: String? = null,
    @SerializedName("des_facet")        val desFacet: List<String> = listOf(),
    @SerializedName("org_facet")        val orgFacet: List<String> = listOf(),
    @SerializedName("per_facet")        val perFacet: List<String> = listOf(),
    @SerializedName("geo_facet")        val geoFacet: List<String> = listOf(),
    @SerializedName("media")            val media: List<ArticleResponseMediaItem> = listOf(),
    @SerializedName("eta_id")           val etaId: Int? = null,
)

data class ArticleResponseMediaItem(
    @SerializedName("type")                         val type: String? = null,
    @SerializedName("subtype")                      val subtype: String? = null,
    @SerializedName("caption")                      val caption: String? = null,
    @SerializedName("copyright")                    val copyright: String? = null,
    @SerializedName("approved_for_syndication")     val approvedForSyndication: Int? = null,
    @SerializedName("media-metadata")               val metadata: List<ArticleResponseMediaMetaDataItem> = emptyList(),
)

data class ArticleResponseMediaMetaDataItem(
    @SerializedName("url")       val url: String? = null,
    @SerializedName("format")    val format: String? = null,
    @SerializedName("height")    val height: Int? = null,
    @SerializedName("width")     val width: Int? = null,
)