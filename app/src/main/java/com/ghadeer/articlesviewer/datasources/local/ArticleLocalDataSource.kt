package com.ghadeer.articlesviewer.datasources.local

import com.ghadeer.articlesviewer.database.AppDatabase
import com.ghadeer.articlesviewer.database.entities.ArticleEntity

class ArticleLocalDataSource(private val appDatabase: AppDatabase) {

    suspend fun getArticlesList(searchQuery: String): List<ArticleEntity> {
        return appDatabase.articleDao().getArticles(searchQuery)
    }

    suspend fun addArticlesList(articleEntitiesList: List<ArticleEntity>) {
        return appDatabase.articleDao().insert(articleEntitiesList)
    }

    suspend fun addArticle(articleEntity: ArticleEntity) {
        return appDatabase.articleDao().insert(articleEntity)
    }

    suspend fun getArticleById(articleId: Long): ArticleEntity? {
        return appDatabase.articleDao().getArticleById(articleId)
    }

    suspend fun deleteAll() {
        appDatabase.articleDao().deleteAll()
    }

    suspend fun isTableEmpty(): Boolean {
        return appDatabase.articleDao().isTableEmpty()
    }
}