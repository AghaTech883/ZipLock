package com.example.zipperscreenlocker.core.base.activity

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.example.zipperscreenlocker.di.setup.DIComponent
import com.example.zipperscreenlocker.ui.viewmodel.SharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class BaseActivity<T : ViewBinding>(bindingFactory: (LayoutInflater) -> T) : ParentActivity<T>(bindingFactory) {

    protected val diComponent by lazy { DIComponent() }
    protected val sharedViewModel by viewModel<SharedViewModel>()
}