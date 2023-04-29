package com.ghadeer.articlesviewer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ghadeer.articlesviewer.database.dao.ArticleDao
import com.ghadeer.articlesviewer.database.entities.ArticleEntity


@Database(
    entities = [
        ArticleEntity::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoomTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        private const val TAG = "AppDatabase"
        private const val DB_NAME = "articles_db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) { buildDatabase(context) }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DB_NAME
            ).fallbackToDestructiveMigration()
                .build()

            return INSTANCE!!
        }
    }

    abstract fun articleDao(): ArticleDao
}