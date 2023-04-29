package com.ghadeer.articlesviewer

import com.ghadeer.articlesviewer.data.models.Media
import com.ghadeer.articlesviewer.database.entities.ArticleEntity
import com.ghadeer.articlesviewer.network.responses.ArticleResponse
import com.ghadeer.articlesviewer.network.responses.ArticleResponseMediaItem
import com.ghadeer.articlesviewer.utils.Mapper
import com.ghadeer.articlesviewer.utils.formatDate
import org.junit.Test

import org.junit.Assert.*

class HelperFunctionsTest {

    @Test
    fun testcase_formatDate() {

        val formattedDate1 = "2023-04-27".formatDate()
        val formattedDate2 = "2024-07-15".formatDate()
        val formattedDate3 = "2018-11-02".formatDate()

        assertTrue(
            "27 Apr 2023" == formattedDate1
                    && "15 Jul 2024" == formattedDate2
                    && "02 Nov 2018" == formattedDate3
        )
    }

    @Test
    fun testcase_convertArticleResponseToArticleEntity(){
        val articleResponse = ArticleResponse(
            uri = "uri11",
            url = "url11",
            id = 1L,
            assetId = 12L,
            source = "source11",
            publishedDate = "2023-04-27",
            updated = "updated11",
            section = "section11",
            subsection = "subsection11",
            nytdSection = "nytdSection11",
            adxKeywords = "adxKeywords11",
            column = "column11",
            byline = "byline11",
            type = "type11",
            title = "title11",
            abstract = "abstract11",
            desFacet = listOf("1", "2"),
            orgFacet = listOf("1", "2", "3", "4"),
            perFacet = listOf("1", "2", "3", "4", "5", "6"),
            geoFacet = listOf("1", "2", "3"),
            media = listOf(ArticleResponseMediaItem()),
            etaId = 0
        )

        val articleEntity = Mapper.articleResponseToArticleEntity(articleResponse)
        assertTrue(
            articleEntity.uri == "uri11"
                    && articleEntity.url == "url11"
                    && articleEntity.id == 1L
                    && articleEntity.assetId == 12L
                    && articleEntity.source == "source11"
                    && articleEntity.publishedDate == "2023-04-27"
                    && articleEntity.updated == "updated11"
                    && articleEntity.section == "section11"
                    && articleEntity.subsection == "subsection11"
                    && articleEntity.nytdSection == "nytdSection11"
                    && articleEntity.adxKeywords == "adxKeywords11"
                    && articleEntity.column == "column11"
                    && articleEntity.byline == "byline11"
                    && articleEntity.type == "type11"
                    && articleEntity.title == "title11"
                    && articleEntity.abstract == "abstract11"
                    && articleEntity.desFacet.size == 2
                    && articleEntity.orgFacet.size == 4
                    && articleEntity.perFacet.size == 6
                    && articleEntity.geoFacet.size == 3
                    && articleEntity.media.size == 1
                    && articleEntity.etaId == 0
        )
    }


    @Test
    fun testcase_convertArticleEntityToArticleModel(){
        val articleEntity = ArticleEntity(
            uri = "uri11",
            url = "url11",
            id = 1L,
            assetId = 12L,
            source = "source11",
            publishedDate = "2023-04-27",
            updated = "updated11",
            section = "section11",
            subsection = "subsection11",
            nytdSection = "nytdSection11",
            adxKeywords = "adxKeywords11",
            column = "column11",
            byline = "byline11",
            type = "type11",
            title = "title11",
            abstract = "abstract11",
            desFacet = listOf("1", "2"),
            orgFacet = listOf("1", "2", "3", "4"),
            perFacet = listOf("1", "2", "3", "4", "5", "6"),
            geoFacet = listOf("1", "2", "3"),
            media = listOf(Media()),
            etaId = 0
        )

        val articleModel = Mapper.articleEntityToArticleModel(articleEntity)
        assertTrue(
            articleModel.uri == "uri11"
                    && articleModel.url == "url11"
                    && articleModel.id == 1L
                    && articleModel.assetId == 12L
                    && articleModel.source == "source11"
                    && articleModel.publishedDate == "2023-04-27"
                    && articleModel.updated == "updated11"
                    && articleModel.section == "section11"
                    && articleModel.subsection == "subsection11"
                    && articleModel.nytdSection == "nytdSection11"
                    && articleModel.adxKeywords == "adxKeywords11"
                    && articleModel.column == "column11"
                    && articleModel.byline == "byline11"
                    && articleModel.type == "type11"
                    && articleModel.title == "title11"
                    && articleModel.abstract == "abstract11"
                    && articleModel.desFacet.size == 2
                    && articleModel.orgFacet.size == 4
                    && articleModel.perFacet.size == 6
                    && articleModel.geoFacet.size == 3
                    && articleModel.media.size == 1
                    && articleModel.etaId == 0
        )
    }
}