package com.ghadeer.articlesviewer.network

import com.ghadeer.articlesviewer.network.responses.ArticlesResponse
import com.ghadeer.articlesviewer.utils.Constants
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @GET("mostviewed/all-sections/{period}.json")
    suspend fun getMostViewedArticlesList(
        @Path("period") period: Int
    ): Response<ArticlesResponse>
}