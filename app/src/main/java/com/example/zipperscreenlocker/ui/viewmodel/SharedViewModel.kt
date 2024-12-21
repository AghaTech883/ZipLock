package com.example.zipperscreenlocker.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.zipperscreenlocker.data.repository.MainRepository
import com.example.zipperscreenlocker.utilities.observers.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SharedViewModel(private val mainRepository: MainRepository) : ViewModel() {
    private val _splashLoadingLiveData = SingleLiveEvent<Boolean>()
    val splashLoadingLiveData: LiveData<Boolean> get() = _splashLoadingLiveData
    private var splashLoadingJob: Job? = null
    private var elapsedTime = 0L // Time already spent in milliseconds
    private var totalDuration = 6000L // Total duration of the splash delay in milliseconds

    fun splashLoading() {
        splashLoadingJob = CoroutineScope(Dispatchers.Default).launch {
            val remainingTime = totalDuration - elapsedTime
            if (remainingTime > 0) {
                delay(remainingTime)
            }
            _splashLoadingLiveData.postValue(true)
        }
    }

    fun cancelSplashLoading() {
        splashLoadingJob?.cancel()
    }

    fun updateElapsedTime(startTime: Long) {
        elapsedTime += System.currentTimeMillis() - startTime
    }

    fun resetElapsedTime() {
        elapsedTime = 0L
    }

}