package com.example.zipperscreenlocker.di.domain.observer

import androidx.lifecycle.LiveData
import androidx.navigation.NavDirections
import com.example.zipperscreenlocker.utilities.observers.SingleLiveEvent

class GeneralObserver {
    val _navDashboardLiveData = SingleLiveEvent<Int>()
    val navDashboardLiveData: LiveData<Int> get() = _navDashboardLiveData

    val _navDashboardDirectionLiveData = SingleLiveEvent<NavDirections>()
    val navDashboardDirectionLiveData: LiveData<NavDirections> get() = _navDashboardDirectionLiveData
}