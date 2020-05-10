package com.example.testseekeritunes.view.main

import com.example.testseekeritunes.core.BaseViewModel

class MainViewModel : BaseViewModel<MainViewStatus>() {
    override fun getInitialViewState(): MainViewStatus = MainViewStatus()
}