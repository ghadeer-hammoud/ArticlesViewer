package com.ghadeer.articlesviewer.repos

import com.ghadeer.articlesviewer.data.DataResult
import com.ghadeer.articlesviewer.data.NetworkResult
import com.ghadeer.articlesviewer.database.entities.ArticleEntity
import com.ghadeer.articlesviewer.datasources.local.ArticleLocalDataSource
import com.ghadeer.articlesviewer.datasources.remote.ArticleRemoteDataSource
import com.ghadeer.articlesviewer.utils.Mapper
import javax.inject.Inject

class ArticleRepository @Inject constructor(
    private val articleRemoteDataSource: ArticleRemoteDataSource,
    private val articleLocalDataSource: ArticleLocalDataSource
) {


    private suspend fun loadArticles(
        period: Int,
    ): DataResult<String>{

        return when(val netResult = articleRemoteDataSource.fetchArticlesList(period)){
            is NetworkResult.Success -> {
                val list = netResult.data?.results?.map { Mapper.articleResponseToArticleEntity(it) }
                list?.let {
                    articleLocalDataSource.deleteAll()
                    articleLocalDataSource.addArticlesList(it)
                }
                DataResult.Success("Success")
            }
            is NetworkResult.Failure -> {
                DataResult.Error(netResult.message)
            }
            is NetworkResult.Error -> {
                DataResult.Error("Error fetching articles.")
            }
        }
    }
    suspend fun getArticlesList(period: Int,
                                searchQuery: String,
                                forceUpdate:Boolean = false): DataResult<List<ArticleEntity>> {

        val message = if(forceUpdate || articleLocalDataSource.isTableEmpty()){
            when(val res = loadArticles(period)){
                is DataResult.Success -> ""
                is DataResult.Error -> res.message
            }
        } else
            ""

        return DataResult.Success(articleLocalDataSource.getArticlesList(searchQuery), message)
    }


    suspend fun getArticleById(articleId: Long): DataResult<ArticleEntity> {

        articleLocalDataSource.getArticleById(articleId).let {
            return when(it){
                null -> DataResult.Error("Article not found.")
                else -> DataResult.Success(it)
            }
        }
    }
}