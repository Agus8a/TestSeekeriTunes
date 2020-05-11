package com.example.testseekeritunes.di

import com.example.testseekeritunes.view.detail.DetailViewModel
import com.example.testseekeritunes.view.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel(get(), get()) }
    viewModel { DetailViewModel() }
}