package com.example.testseekeritunes.view.main

import com.example.domain.model.Result
import com.example.testseekeritunes.core.BaseViewStatus

class MainViewStatus(
    var resultList: List<Result> = listOf(),
    var termList: List<String> = listOf()
) : BaseViewStatus()