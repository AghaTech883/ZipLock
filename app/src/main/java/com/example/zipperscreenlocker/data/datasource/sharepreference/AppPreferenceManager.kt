package com.example.zipperscreenlocker.data.datasource.sharepreference

import android.content.Context
import android.content.SharedPreferences
import com.example.zipperscreenlocker.di.domain.observer.GeneralObserver

class AppPreferenceManager(context: Context, private val generalObserver: GeneralObserver) {
    private val PREF_NAME = "app_preferences"

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    init {
        // initialize all preference that are first
    }

}