package com.example.zipperscreenlocker.utilities.extensions

import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.onBackPressedDispatcher(callback: () -> Unit) {
    onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            callback.invoke()
        }
    })
}