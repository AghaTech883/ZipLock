package com.example.zipperscreenlocker.ui.fragments

import android.view.View
import android.widget.Toast
import com.example.zipperscreenlocker.R
import com.example.zipperscreenlocker.core.base.fragment.BaseFragment
import com.example.zipperscreenlocker.databinding.FragmentMainBinding
import com.example.zipperscreenlocker.domain.biometric.BiometricHelper
import com.example.zipperscreenlocker.domain.customview.ZipperLockView
import com.example.zipperscreenlocker.utilities.extensions.navigateTo
import com.example.zipperscreenlocker.utilities.extensions.onBackPressedDispatcher


class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    private val biometricHelper by lazy {
        BiometricHelper(globalContext)
    }
    private var backPressedTime: Long = 0
    private var backToast: Toast? = null

    override fun onViewCreated() {
        initZipperView()
        buttonClickListener()
    }

    private fun initZipperView() {
        binding.zipperLockView.setZipperStateCallback(object : ZipperLockView.ZipperStateCallback {
            override fun onZipperStateChanged(isOpen: Boolean) {
                if (isOpen) {
                    // Handle the zipper being fully open (e.g., prompt biometric authentication)
                    authenticate()
                } else {
                    // Handle the zipper being fully closed
                }
            }
        })

    }

    private fun authenticate() {
        biometricHelper.authenticate(
            activity = requireActivity(),
            title = "Verify Identity",
            subtitle = "Access your account securely",
            description = "Use your fingerprint or face ID for authentication",
            onSuccess = {
                navigateTo(R.id.mainFragment, R.id.action_mainFragment_to_resultFragment)
            },
            onFailure = {
                Toast.makeText(requireContext(), "Authentication Failed.", Toast.LENGTH_SHORT)
                    .show()
                // Handle failed authentication
                binding.zipperLockView.forceClose()
            },
            onError = { errorCode, errorMessage ->
                Toast.makeText(
                    requireContext(),
                    "Error ($errorCode): $errorMessage",
                    Toast.LENGTH_SHORT
                ).show()
                // Handle authentication errors
            },
            onUnavailable = { reason ->
                Toast.makeText(requireContext(), reason, Toast.LENGTH_SHORT).show()
                // Handle the case where biometrics are not available
            }
        )
    }

    private fun buttonClickListener() {
        with(binding) {

            openZipLockButton.setOnClickListener {
                checkBiometricFirst()

            }
        }
    }

    private fun handlePortraitModeBackPress() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            // If back is pressed twice within 2 seconds, finish the activity
            backToast?.cancel()  // Cancel the toast if it's still showing
            activity?.finish()
        } else {
            // Show a toast on the first back press
            backToast = Toast.makeText(requireContext(), "Press again to exit", Toast.LENGTH_SHORT)
            backToast?.show()
        }
        // Update the backPressedTime to current time
        backPressedTime = System.currentTimeMillis()
    }

    override fun onResume() {
        super.onResume()
        registerBackPress()
    }

    private fun registerBackPress() {
        onBackPressedDispatcher {
            handlePortraitModeBackPress()
        }
    }

    private fun checkBiometricFirst() {
        if (!biometricHelper.isBiometricAvailable()) {
            Toast.makeText(
                requireContext(),
                "Biometric authentication is not available.",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            binding.openZipLockButton.visibility = View.GONE
            binding.zipperLockView.visibility = View.VISIBLE
        }
    }

}