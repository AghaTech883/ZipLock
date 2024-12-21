package com.example.zipperscreenlocker.ui.activity

import android.content.IntentFilter
import android.location.LocationManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.zipperscreenlocker.R
import com.example.zipperscreenlocker.core.base.activity.BaseActivity
import com.example.zipperscreenlocker.databinding.ActivityMainBinding
import com.example.zipperscreenlocker.utilities.extensions.onBackPressedDispatcher

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val navController by lazy { (supportFragmentManager.findFragmentById(binding.fcvContainerMain.id) as NavHostFragment).navController }


    override fun onPreCreated() {
        super.onPreCreated()
        installSplashTheme()

    }

    override fun onCreated() {
        registerBackPress()
        navController.addOnDestinationChangedListener(destinationChangeListener)
        initObserver()

    }

    private fun initObserver() {

    }

    private fun registerBackPress() {
        onBackPressedDispatcher {
            when (navController.currentDestination?.id) {
                R.id.mainFragment,
                    -> {
                }

                else -> navController.popBackStack()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    private val destinationChangeListener = NavController.OnDestinationChangedListener { _, destination, _ ->
        when (destination.id) {
            R.id.mainFragment -> {
                includeTopPadding = false
                includeBottomPadding = false
            }

            else -> {
                includeTopPadding = true
                includeBottomPadding = true
            }
        }
    }


}