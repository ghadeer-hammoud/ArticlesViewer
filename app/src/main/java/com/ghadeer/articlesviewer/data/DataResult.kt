package com.ghadeer.articlesviewer.data

sealed class DataResult<out R> {
    data class Success<out T>(val data: T, val message: String = "") : DataResult<T>()
    data class Error(val message: String) : DataResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[Message=$message]"
        }
    }
}
