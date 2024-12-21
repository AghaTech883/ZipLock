package com.example.zipperscreenlocker.di.setup

import com.example.zipperscreenlocker.di.domain.observer.GeneralObserver
import com.example.zipperscreenlocker.data.datasource.sharepreference.AppPreferenceManager
import com.example.zipperscreenlocker.data.repository.MainRepository
import com.example.zipperscreenlocker.ui.viewmodel.SharedViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.lazyModule
import org.koin.dsl.module

class KoinModules {

    /* ________________________________ Main Thread Modules ________________________________ */

    private val mainModules = module {
        single { AppPreferenceManager(androidContext(), get()) }
    }
    /* ________________________________ View Model Main Modules ________________________________ */

    private val viewModelModule = module {
        single { MainRepository(androidContext()) }
        viewModel { SharedViewModel(get()) }
    }
    /* ________________________________ Background Thread Modules ________________________________ */

    @OptIn(KoinExperimentalAPI::class)
    private val domainModules = lazyModule {
        single { GeneralObserver() }
    }
    /* ________________________________ Modules List ________________________________ */

    val mainModuleList = listOf(mainModules,viewModelModule)
    val backgroundModuleList = listOf(domainModules)

}