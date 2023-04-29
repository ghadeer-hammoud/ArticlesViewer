package com.ghadeer.articlesviewer

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ghadeer.articlesviewer.data.NetworkResult
import com.ghadeer.articlesviewer.datasources.remote.ArticleRemoteDataSource
import com.ghadeer.articlesviewer.network.ApiClient
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArticleRemoteDataSourceTest: TestCase() {

    private lateinit var articleRemoteDataSource: ArticleRemoteDataSource

    @Before
    public override fun setUp() {
        val apiInterface = ApiClient().getClient()
        articleRemoteDataSource = ArticleRemoteDataSource(apiInterface)
    }

    @After
    fun endUp() {
    }

    @Test
    fun testcase_successFetchArticlesList() = runBlocking{
        val result = articleRemoteDataSource.fetchArticlesList(7)
        assertTrue(result is NetworkResult.Success && result.data != null)
    }
}