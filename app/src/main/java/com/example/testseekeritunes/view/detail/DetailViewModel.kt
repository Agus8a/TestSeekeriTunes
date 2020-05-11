package com.example.testseekeritunes.view.detail

import com.example.testseekeritunes.core.BaseViewModel

class DetailViewModel : BaseViewModel<DetailViewStatus>() {
    override fun getInitialViewState(): DetailViewStatus = DetailViewStatus()

    fun load(url: String) {
        resourceViewState.value = DetailViewStatus(url)
    }
}