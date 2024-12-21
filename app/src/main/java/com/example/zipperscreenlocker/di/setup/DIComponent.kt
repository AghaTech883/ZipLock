package com.example.zipperscreenlocker.di.setup

import com.example.zipperscreenlocker.data.datasource.sharepreference.AppPreferenceManager
import com.example.zipperscreenlocker.di.domain.observer.GeneralObserver
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject

class DIComponent : KoinComponent {

    // Observers
    val generalObserver by inject<GeneralObserver>()

    // preferences
    val preferences: AppPreferenceManager = get()
}