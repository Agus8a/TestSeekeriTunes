package com.example.domain.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val domainModule = module {

    single { CoroutineScope(Dispatchers.IO) }
}