package com.example.testseekeritunes.screens

import com.example.data.di.dataModule
import com.example.domain.di.domainModule
import com.example.testseekeritunes.di.appModule
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.mockito.MockitoAnnotations

class MainViewModelTest : KoinTest {
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        startKoin {
            printLogger()
            modules(listOf(appModule, domainModule, dataModule))
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun suggest() {
    }

    @Test
    fun search() {
    }
}