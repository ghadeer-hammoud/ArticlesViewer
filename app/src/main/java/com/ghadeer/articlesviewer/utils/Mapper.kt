package com.ghadeer.articlesviewer.utils

import com.ghadeer.articlesviewer.data.models.Article
import com.ghadeer.articlesviewer.data.models.Media
import com.ghadeer.articlesviewer.data.models.MediaMetaData
import com.ghadeer.articlesviewer.database.entities.ArticleEntity
import com.ghadeer.articlesviewer.network.responses.ArticleResponse


object Mapper{

    fun articleResponseToArticleEntity(articleResponse: ArticleResponse) = 
        ArticleEntity(
            uri = articleResponse.uri ?: "",
            url = articleResponse.url ?: "",
            id = articleResponse.id ?: 0,
            assetId = articleResponse.assetId ?: 0,
            source = articleResponse.source ?: "",
            publishedDate = articleResponse.publishedDate ?: "",
            updated = articleResponse.updated ?: "",
            section = articleResponse.section ?: "",
            subsection = articleResponse.subsection ?: "",
            nytdSection = articleResponse.nytdSection ?: "",
            adxKeywords = articleResponse.adxKeywords ?: "",
            column = articleResponse.column ?: "",
            byline = articleResponse.byline ?: "",
            type = articleResponse.type ?: "",
            title = articleResponse.title ?: "",
            abstract = articleResponse.abstract ?: "",
            desFacet = articleResponse.desFacet,
            orgFacet = articleResponse.orgFacet,
            perFacet = articleResponse.perFacet,
            geoFacet = articleResponse.geoFacet,
            media = articleResponse.media.map { mediaItem ->
                Media(
                    type = mediaItem.type ?: "",
                    subtype = mediaItem.subtype ?: "",
                    caption = mediaItem.caption ?: "",
                    copyright = mediaItem.copyright ?: "",
                    approvedForSyndication = mediaItem.approvedForSyndication ?: 0,
                    metadata = mediaItem.metadata.map { metadataItem ->
                        MediaMetaData(
                            url = metadataItem.url ?: "",
                            format = metadataItem.format ?: "",
                            height = metadataItem.height ?: 0,
                            width = metadataItem.width ?: 0,

                            )
                    },

                    )
            },
            etaId = articleResponse.etaId ?: 0
        )

    fun articleEntityToArticleModel(articleEntity: ArticleEntity) = 
         Article(
            uri = articleEntity.uri,
            url = articleEntity.url,
            id = articleEntity.id,
            assetId = articleEntity.assetId,
            source = articleEntity.source,
            publishedDate = articleEntity.publishedDate,
            updated = articleEntity.updated,
            section = articleEntity.section,
            subsection = articleEntity.subsection,
            nytdSection = articleEntity.nytdSection,
            adxKeywords = articleEntity.adxKeywords,
            column = articleEntity.column,
            byline = articleEntity.byline,
            type = articleEntity.type,
            title = articleEntity.title,
            abstract = articleEntity.abstract,
            desFacet = articleEntity.desFacet,
            orgFacet = articleEntity.orgFacet,
            perFacet = articleEntity.perFacet,
            geoFacet = articleEntity.geoFacet,
            media = articleEntity.media,
            etaId = articleEntity.etaId,

        )
    
}
