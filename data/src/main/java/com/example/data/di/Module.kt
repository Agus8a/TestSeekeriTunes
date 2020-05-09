package com.example.data.di

import com.example.data.datasource.ResultDataSource
import com.example.data.datasource.SearchDataSource
import com.example.data.datasource.TermDataSource
import com.example.data.datasource.local.LocalResultDataSource
import com.example.data.datasource.local.LocalTermDataSource
import com.example.data.datasource.local.db.TestSeekeriTunesDatabase
import com.example.data.datasource.remote.ApiProvider
import com.example.data.datasource.remote.RemoteSearchDataSource
import com.example.data.mapper.ResultEntityToModel
import com.example.data.mapper.ResultModelToEntity
import com.example.data.repository.SearchRepositoryImpl
import com.example.domain.repository.SearchRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single<ResultDataSource> { LocalResultDataSource(get()) }
    single<TermDataSource> { LocalTermDataSource(get()) }

    single<SearchDataSource> { RemoteSearchDataSource(get()) }

    single<SearchRepository> { SearchRepositoryImpl(get(), get(), get(), get()) }

    single { ResultEntityToModel() }
    single { ResultModelToEntity() }

    single { ApiProvider(androidContext()) }
    single {
        val apiProvider: ApiProvider = get()
        apiProvider.api
    }
    single { TestSeekeriTunesDatabase.getAppDatabase(androidContext()) }
}