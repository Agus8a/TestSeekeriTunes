package com.example.testseekeritunes.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

abstract class BaseActivity<VS, VM : BaseViewModel<VS>> : AppCompatActivity() {
    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = initViewModel()
        viewModel.resourceViewState.observe(this, viewStatusObserver)
        setContentView(getLayoutResource())
    }

    private val viewStatusObserver = Observer<VS> {
        onViewStatusUpdated(it)
    }

    abstract fun initViewModel(): VM
    abstract fun getLayoutResource(): Int
    abstract fun onViewStatusUpdated(viewStatus: VS)
}