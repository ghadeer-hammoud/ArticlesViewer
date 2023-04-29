package com.ghadeer.articlesviewer

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ghadeer.articlesviewer.data.DataResult
import com.ghadeer.articlesviewer.data.NetworkResult
import com.ghadeer.articlesviewer.database.AppDatabase
import com.ghadeer.articlesviewer.database.entities.ArticleEntity
import com.ghadeer.articlesviewer.datasources.local.ArticleLocalDataSource
import com.ghadeer.articlesviewer.datasources.remote.ArticleRemoteDataSource
import com.ghadeer.articlesviewer.network.ApiClient
import com.ghadeer.articlesviewer.repos.ArticleRepository
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArticleRepositoryTest: TestCase() {

    private lateinit var articleRepository: ArticleRepository

    private lateinit var articleRemoteDataSource: ArticleRemoteDataSource
    private lateinit var articleLocalDataSource: ArticleLocalDataSource

    @Before
    public override fun setUp() {
        val apiInterface = ApiClient().getClient()
        articleRemoteDataSource = ArticleRemoteDataSource(apiInterface)

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val appDatabase = Room.inMemoryDatabaseBuilder(
            appContext, AppDatabase::class.java
        ).build()
        articleLocalDataSource = ArticleLocalDataSource(appDatabase)

        articleRepository = ArticleRepository(articleRemoteDataSource, articleLocalDataSource)
    }

    @After
    fun endUp() {
    }

    @Test
    fun testcase_getArticlesFromLocalSource() = runBlocking{

        // Feed articles table
        articleLocalDataSource.apply {
            addArticle(ArticleEntity().apply { id = 1 })
            addArticle(ArticleEntity().apply { id = 2 })
            addArticle(ArticleEntity().apply { id = 3 })
        }

        val result = articleRepository.getArticlesList(period = 7, searchQuery = "")
        assertTrue(result is DataResult.Success && result.data.size == 3)
    }

    @Test
    fun testcase_getArticlesFromLocalSourceWithSearch() = runBlocking{

        // Feed articles table
        articleLocalDataSource.apply {
            addArticle(ArticleEntity().apply { id = 1; title = "hello" })
            addArticle(ArticleEntity().apply { id = 2; title = "hello there"  })
            addArticle(ArticleEntity().apply { id = 3; title = "bye"  })
        }

        val searchQuery = "hello"

        val result = articleRepository.getArticlesList(period = 7, searchQuery = searchQuery)
        assertTrue(result is DataResult.Success && result.data.size == 2)
    }

    @Test
    fun testcase_loadArticlesFromRemoteSourceIfLocalTableIsEmpty() = runBlocking{

        // Empty articles table
        articleLocalDataSource.deleteAll()
        val sizeAfterClear = articleLocalDataSource.getArticlesList("").size

        // ForceUpdate is false
        val result = articleRepository.getArticlesList(period = 7, searchQuery = "", forceUpdate = false)
        assertTrue(sizeAfterClear == 0
                && result is DataResult.Success && result.data.isNotEmpty())
    }

    @Test
    fun testcase_loadArticlesFromRemoteSourceIfForceUpdateIsTrue() = runBlocking{

        // Articles table has only 1 entity
        articleLocalDataSource.addArticle(ArticleEntity().apply { id = 1 })

        // ForceUpdate is true
        // Articles table will have more entities
        val result = articleRepository.getArticlesList(period = 7, searchQuery = "", forceUpdate = true)
        assertTrue(result is DataResult.Success && result.data.size > 1)
    }

    @Test
    fun testcase_getExistingArticleFromLocalSourceById() = runBlocking{

        val articleAID = 1234L
        articleLocalDataSource.addArticle(ArticleEntity().apply { id = articleAID })

        val result = articleRepository.getArticleById(articleAID)
        assertTrue(result is DataResult.Success && result.data.id == articleAID)
    }

    @Test
    fun testcase_getNonExistingArticleFromLocalSourceById() = runBlocking{

        val articleAID = 1234L
        val anotherArticleID = 5678L
        articleLocalDataSource.addArticle(ArticleEntity().apply { id = articleAID })

        val result = articleRepository.getArticleById(anotherArticleID)
        assertTrue(result is DataResult.Error && result.message == "Article not found.")
    }
}