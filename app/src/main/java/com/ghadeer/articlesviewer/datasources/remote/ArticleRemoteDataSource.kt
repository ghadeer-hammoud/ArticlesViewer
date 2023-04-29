package com.ghadeer.articlesviewer.datasources.remote

import com.ghadeer.articlesviewer.data.NetworkResult
import com.ghadeer.articlesviewer.network.ApiInterface
import com.ghadeer.articlesviewer.network.responses.ArticlesResponse
import com.ghadeer.articlesviewer.utils.responseDelegate


class ArticleRemoteDataSource(private val apiInterface: ApiInterface) {

    suspend fun fetchArticlesList(period: Int): NetworkResult<ArticlesResponse?> {
        return try {
            responseDelegate(apiInterface.getMostViewedArticlesList(period = period))
        } catch (e: Exception) {
            NetworkResult.Failure("Something Went Wrong.\n[${e.localizedMessage}]")
        }
    }
}