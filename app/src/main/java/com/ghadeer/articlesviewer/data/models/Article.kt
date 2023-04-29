package com.ghadeer.articlesviewer.data.models


data class Article(
    val uri: String = "",
    val url: String = "",
    val id: Long = 0,
    val assetId: Long = 0,
    val source: String = "",
    val publishedDate: String = "",
    val updated: String = "",
    val section: String = "",
    val subsection: String = "",
    val nytdSection: String = "",
    val adxKeywords: String = "",
    val column: String = "",
    val byline: String = "",
    val type: String = "",
    val title: String = "",
    val abstract: String = "",
    val desFacet: List<String> = emptyList(),
    val orgFacet: List<String> = emptyList(),
    val perFacet: List<String> = emptyList(),
    val geoFacet: List<String> = emptyList(),
    val media: List<Media> = emptyList(),
    val etaId: Int = 0,
)
