package com.ghadeer.articlesviewer.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.RawQuery
import androidx.room.Update
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery

abstract class BaseDao<T>(private val tableName: String) {

    @Insert(onConflict = REPLACE)
    abstract suspend fun insert(entity: T)

    @Insert(onConflict = REPLACE)
    abstract suspend fun insert(entities: List<T>)

    @Update
    abstract suspend fun update(entity: T)

    @Update
    abstract suspend fun update(entities: List<T>)

    @Delete
    abstract suspend fun delete(entity: T)

    @Delete
    abstract suspend fun delete(entities: List<T>)

    @RawQuery
    abstract suspend fun deleteAll(query: SupportSQLiteQuery): Int

    suspend fun deleteAll() {
        val query = SimpleSQLiteQuery("DELETE FROM $tableName")
        deleteAll(query)
    }
}
