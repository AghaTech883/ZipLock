package com.example.zipperscreenlocker.ui.fragments

import android.view.View
import com.example.zipperscreenlocker.R
import com.example.zipperscreenlocker.core.base.fragment.BaseFragment
import com.example.zipperscreenlocker.databinding.FragmentSplashBinding
import com.example.zipperscreenlocker.utilities.extensions.launchWhenResumed
import com.example.zipperscreenlocker.utilities.extensions.navigateTo


class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {
    private var splashStartTime = 0L

    override fun onViewCreated() {
        initObserver()
        buttonClickListener()
    }

    private fun initObserver() {
        sharedViewModel.splashLoadingLiveData.observe(viewLifecycleOwner) {
            launchWhenResumed { binding.getStartedSplash.visibility = View.VISIBLE }
        }
    }

    private fun buttonClickListener() {
        binding.getStartedSplash.setOnClickListener {
            navigateTo(R.id.splashFragment, R.id.action_splashFragment_to_mainFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        splashStartTime = System.currentTimeMillis() // Record start time
        sharedViewModel.splashLoading()
    }

    override fun onPause() {
        super.onPause()
        sharedViewModel.cancelSplashLoading()
        sharedViewModel.updateElapsedTime(splashStartTime)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        sharedViewModel.resetElapsedTime() // Reset when fragment is destroyed
    }


}