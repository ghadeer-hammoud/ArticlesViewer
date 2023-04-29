package com.ghadeer.articlesviewer.utils

import androidx.navigation.NavOptions
import com.ghadeer.articlesviewer.R

object Constants {

    const val BASE_URL = "https://api.nytimes.com/svc/mostpopular/v2/"
    const val API_KEY = "mQ977vcpxuAuODbJlfVOvHaBkpq2n6Gc"
    const val DEFAULT_PERIOD = 7

    val NAV_OPTIONS = NavOptions.Builder()
        .setLaunchSingleTop(true)  // Used to prevent multiple copies of the same destination
        .setEnterAnim(R.anim.slide_in_right)
        .setExitAnim(R.anim.slide_out_left)
        .setPopEnterAnim(R.anim.slide_in_left)
        .setPopExitAnim(R.anim.slide_out_right)
        .build()
}