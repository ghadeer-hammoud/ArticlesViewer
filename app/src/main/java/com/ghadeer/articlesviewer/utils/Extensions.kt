package com.ghadeer.articlesviewer.utils

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun Activity.toast(message: String, length: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, message, length).show()
}

fun Fragment.toast(message: String, length: Int = Toast.LENGTH_SHORT){
    requireActivity().toast(message, length)
}

fun ImageView.loadImage(url: String) {
    Glide.with(this)
        .load(url)
        .into(this)
}

fun String.formatDate(
    baseFormat: String = "yyyy-MM-dd",
    newFormat: String = "dd MMM yyyy"
): String {
    return try {
        val baseDateFormat = SimpleDateFormat(baseFormat, Locale.US)
        val date = baseDateFormat.parse(this)
        val newDateFormat = SimpleDateFormat(newFormat, Locale.US)
        newDateFormat.format(date)
    } catch (e: Exception) {
        this
    }
}
