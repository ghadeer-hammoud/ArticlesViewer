package com.ghadeer.articlesviewer.ui.states

import com.ghadeer.articlesviewer.data.enums.Status

data class MainState<out T>(
    val progress: Boolean = false,
    val status: Status = Status.Idle,
    val data: T? = null,
    val message: String = ""
) {
    fun empty() = MainState<T>()
    fun showProgress() = copy(status = Status.Idle, progress = true)
}
