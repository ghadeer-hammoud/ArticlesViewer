package com.ghadeer.articlesviewer.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.ghadeer.articlesviewer.database.entities.ArticleEntity

@Dao
abstract class ArticleDao : BaseDao<ArticleEntity>("ArticleEntity") {

    @Query("SELECT * FROM ArticleEntity WHERE title LIKE '%' || :searchQuery || '%'")
    abstract suspend fun getArticles(searchQuery: String): List<ArticleEntity>

    @Query("SELECT * FROM ArticleEntity WHERE id = :articleId LIMIT 1")
    abstract suspend fun getArticleById(articleId: Long): ArticleEntity?


    @Query("SELECT (SELECT COUNT(*) FROM ArticleEntity) == 0")
    abstract suspend fun isTableEmpty(): Boolean
}