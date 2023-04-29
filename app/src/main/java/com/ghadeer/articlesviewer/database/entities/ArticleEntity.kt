package com.ghadeer.articlesviewer.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ghadeer.articlesviewer.data.models.Media

@Entity
data class ArticleEntity (
    var uri: String = "",
    var url: String = "",
    @PrimaryKey(autoGenerate = false) 
    var id: Long = 0,
    var assetId: Long = 0,
    var source: String = "",
    var publishedDate: String = "",
    var updated: String = "",
    var section: String = "",
    var subsection: String = "",
    var nytdSection: String = "",
    var adxKeywords: String = "",
    var column: String = "",
    var byline: String = "",
    var type: String = "",
    var title: String = "",
    var abstract: String = "",
    var desFacet: List<String> = emptyList(),
    var orgFacet: List<String> = emptyList(),
    var perFacet: List<String> = emptyList(),
    var geoFacet: List<String> = emptyList(),
    var media: List<Media> = emptyList(),
    var etaId: Int = 0,
)