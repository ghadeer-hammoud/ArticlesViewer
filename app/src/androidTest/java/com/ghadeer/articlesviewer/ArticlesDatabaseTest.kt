package com.ghadeer.articlesviewer

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ghadeer.articlesviewer.database.AppDatabase
import com.ghadeer.articlesviewer.database.dao.ArticleDao
import com.ghadeer.articlesviewer.database.entities.ArticleEntity
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class ArticlesDatabaseTest: TestCase() {

    private lateinit var articleDao: ArticleDao
    private lateinit var appDatabase: AppDatabase

    @Before
    public override fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase = Room.inMemoryDatabaseBuilder(
            appContext, AppDatabase::class.java
        ).build()
        articleDao = appDatabase.articleDao()
    }

    @After
    @Throws(IOException::class)
    fun endUp() {
        appDatabase.close()
    }

    @Test
    fun testcase_insertAndReadArticlesList() = runBlocking {
        feedDatabase()
        val retrievedList = articleDao.getArticles("")
        assertTrue(retrievedList.size == 20)
    }

    @Test
    fun testcase_readExistArticleById() = runBlocking {
        val articleId = 222333444L
        articleDao.insert(ArticleEntity().apply { id = articleId })
        val retrievedArticle = articleDao.getArticleById(articleId)
        assertNotNull(retrievedArticle)
    }

    @Test
    fun testcase_readNonExistArticleById() = runBlocking {
        val articleId = 222333444L
        val anotherArticleId = 111000111L
        articleDao.insert(ArticleEntity().apply { id = articleId })
        val retrievedArticle = articleDao.getArticleById(anotherArticleId)
        assertNull(retrievedArticle)
    }

    @Test
    fun testcase_readArticlesListWithSearch() = runBlocking {

        feedDatabase() // Titles from `1` to `20`
        val filteredListBy1 = articleDao.getArticles("1") // 1, [10 .. 19] (11 results)
        val filteredListBy2 = articleDao.getArticles("2") // 2, 12 (2 results)
        assertTrue(filteredListBy1.size == 11 && filteredListBy2.size == 2)
    }

    @Test
    fun testcase_isTableEmpty() = runBlocking {
        feedDatabase()
        val isTableEmptyBeforeDelete = articleDao.isTableEmpty()
        articleDao.deleteAll()
        val isTableEmptyAfterDelete = articleDao.isTableEmpty()

        assertTrue(!isTableEmptyBeforeDelete && isTableEmptyAfterDelete)
    }

    // Feed database with articles
    // Articles IDs: from 1 to 20
    // Articles titles: from `1` to `20`
    private fun feedDatabase() = runBlocking{

        val articlesList = mutableListOf<ArticleEntity>()
        repeat(20){
            articlesList.add(
                ArticleEntity().apply {
                    id = it.plus(1).toLong()
                    title = it.toString()
                }
            )
        }
        articleDao.insert(articlesList)
    }

}