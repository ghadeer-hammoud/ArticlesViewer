package com.ghadeer.articlesviewer

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ghadeer.articlesviewer.data.NetworkResult
import com.ghadeer.articlesviewer.database.AppDatabase
import com.ghadeer.articlesviewer.database.entities.ArticleEntity
import com.ghadeer.articlesviewer.datasources.local.ArticleLocalDataSource
import com.ghadeer.articlesviewer.datasources.remote.ArticleRemoteDataSource
import com.ghadeer.articlesviewer.network.ApiClient
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArticleLocalDataSourceTest: TestCase() {

    private lateinit var articleLocalDataSource: ArticleLocalDataSource

    @Before
    public override fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val appDatabase = Room.inMemoryDatabaseBuilder(
            appContext, AppDatabase::class.java
        ).build()
        articleLocalDataSource = ArticleLocalDataSource(appDatabase)
    }

    @After
    fun endUp() {
    }

    @Test
    fun testcase_insertAndReadArticlesList() = runBlocking {
        feedArticles()
        val retrievedList = articleLocalDataSource.getArticlesList("")
        assertTrue(retrievedList.size == 20)
    }

    @Test
    fun testcase_readExistArticleById() = runBlocking {
        val articleId = 222333444L
        articleLocalDataSource.addArticle(ArticleEntity().apply { id = articleId })
        val retrievedArticle = articleLocalDataSource.getArticleById(articleId)
        assertNotNull(retrievedArticle)
    }

    @Test
    fun testcase_readNonExistArticleById() = runBlocking {
        val articleId = 222333444L
        val anotherArticleId = 111000111L
        articleLocalDataSource.addArticle(ArticleEntity().apply { id = articleId })
        val retrievedArticle = articleLocalDataSource.getArticleById(anotherArticleId)
        assertNull(retrievedArticle)
    }

    @Test
    fun testcase_readArticlesListWithSearch() = runBlocking {

        feedArticles() // Titles from `1` to `20`
        val filteredListBy1 = articleLocalDataSource.getArticlesList("1") // 1, [10 .. 19] (11 results)
        val filteredListBy2 = articleLocalDataSource.getArticlesList("2") // 2, 12 (2 results)
        assertTrue(filteredListBy1.size == 11 && filteredListBy2.size == 2)
    }

    @Test
    fun testcase_isTableEmpty() = runBlocking {
        feedArticles()
        val isTableEmptyBeforeDelete = articleLocalDataSource.isTableEmpty()
        articleLocalDataSource.deleteAll()
        val isTableEmptyAfterDelete = articleLocalDataSource.isTableEmpty()

        assertTrue(!isTableEmptyBeforeDelete && isTableEmptyAfterDelete)
    }

    // Feed database with articles
    // Articles IDs: from 1 to 20
    // Articles titles: from `1` to `20`
    private fun feedArticles() = runBlocking{

        val articlesList = mutableListOf<ArticleEntity>()
        repeat(20){
            articlesList.add(
                ArticleEntity().apply {
                    id = it.plus(1).toLong()
                    title = it.toString()
                }
            )
        }
        articleLocalDataSource.addArticlesList(articlesList)
    }
}