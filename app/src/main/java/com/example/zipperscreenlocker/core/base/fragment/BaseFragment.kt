package com.example.zipperscreenlocker.core.base.fragment

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.example.zipperscreenlocker.di.setup.DIComponent
import com.example.zipperscreenlocker.ui.viewmodel.SharedViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

abstract class BaseFragment<T : ViewBinding>(bindingFactory: (LayoutInflater) -> T) : ParentFragment<T>(bindingFactory) {

    protected val diComponent by lazy { DIComponent() }

    protected val sharedViewModel by activityViewModel<SharedViewModel>()
}