package com.ghadeer.articlesviewer.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.ghadeer.articlesviewer.database.AppDatabase
import com.ghadeer.articlesviewer.datasources.local.ArticleLocalDataSource
import com.ghadeer.articlesviewer.datasources.remote.ArticleRemoteDataSource
import com.ghadeer.articlesviewer.network.ApiClient
import com.ghadeer.articlesviewer.network.ApiInterface
import com.ghadeer.articlesviewer.repos.ArticleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabaseInstance(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getDatabase(context)

    @Provides
    @Singleton
    fun provideRetrofitInstance(): ApiInterface = ApiClient().getClient()

    @Provides
    @Singleton
    fun provideGlideInstance(@ApplicationContext context: Context): RequestManager =
        Glide.with(context)


    @Provides
    @Singleton
    fun provideArticleRepository(articleRemoteDataSource: ArticleRemoteDataSource, articleLocalDataSource: ArticleLocalDataSource)
            = ArticleRepository(articleRemoteDataSource, articleLocalDataSource)

    @Provides
    @Singleton
    fun provideArticleLocalDataSource(appDatabase: AppDatabase) = ArticleLocalDataSource(appDatabase)

    @Provides
    @Singleton
    fun provideArticleRemoteDataSource(apiInterface: ApiInterface) = ArticleRemoteDataSource(apiInterface)
}