package com.ghadeer.articlesviewer

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ghadeer.articlesviewer.network.ApiClient
import com.ghadeer.articlesviewer.network.ApiInterface
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArticlesApiTest: TestCase() {

    private lateinit var apiInterface: ApiInterface

    @Before
    public override fun setUp() {
        apiInterface = ApiClient().getClient()
    }

    @After
    fun endUp() {
    }

    @Test
    fun testcase_successApiCallWithPeriodValue_1() = runBlocking{
        val response = apiInterface.getMostViewedArticlesList(1)
        assertTrue(response.isSuccessful && response.body() != null)
    }

    @Test
    fun testcase_successApiCallWithPeriodValue_7() = runBlocking{
        val response = apiInterface.getMostViewedArticlesList(7)
        assertTrue(response.isSuccessful && response.body() != null)
    }

    @Test
    fun testcase_successApiCallWithPeriodValue_30() = runBlocking{
        val response = apiInterface.getMostViewedArticlesList(30)
        assertTrue(response.isSuccessful && response.body() != null)
    }

    @Test
    fun testcase_failedApiCallWithPeriodValue_25() = runBlocking{
        val response = apiInterface.getMostViewedArticlesList(25)
        assertTrue(!response.isSuccessful)
    }
}