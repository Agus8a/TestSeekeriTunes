package com.example.testseekeritunes.view.main

import com.example.domain.core.BaseException
import com.example.domain.core.BaseStatusObserver
import com.example.domain.model.Result
import com.example.domain.usecase.SearchUseCase
import com.example.domain.usecase.SuggestTermsUseCase
import com.example.testseekeritunes.core.BaseViewModel

class MainViewModel(
    private val suggestTermsUseCase: SuggestTermsUseCase,
    private val searchUseCase: SearchUseCase
) : BaseViewModel<MainViewStatus>() {
    private var resultList: List<Result> = listOf()
    private var termList: List<String> = listOf()

    override fun getInitialViewState(): MainViewStatus = MainViewStatus()

    fun suggest() {
        val mainViewStatus = getInitialViewState()
        BaseStatusObserver(resourceViewState, suggestTermsUseCase.execute(null), {
            it?.let {
                termList = it
                mainViewStatus.isReady = true
                mainViewStatus.termList = termList
                resourceViewState.value = mainViewStatus
            }
        }, this::onError, this::onLoading)
    }

    fun search(term: String) {
        val mainViewStatus = getInitialViewState()
        BaseStatusObserver(resourceViewState, searchUseCase.execute(term), {
            resultList = it ?: resultList
            mainViewStatus.isComplete = true
            mainViewStatus.resultList = resultList
            resourceViewState.value = mainViewStatus
        }, this::onError, this::onLoading)
    }

    private fun onError(exception: BaseException?) {
        exception?.let {
            val mainViewStatus = getInitialViewState()
            mainViewStatus.isError = true
            mainViewStatus.errorMessage = it.message ?: ""
            resourceViewState.value = mainViewStatus
        }
    }

    private fun onLoading(progress: Int) {
        val mainViewStatus = getInitialViewState()
        mainViewStatus.isLoading = progress > 100
        resourceViewState.value = mainViewStatus
    }
}