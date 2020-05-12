package com.example.testseekeritunes

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.data.di.dataModule
import com.example.domain.di.domainModule
import com.example.testseekeritunes.di.appModule
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
class MainViewModelTest : KoinTest {
    @Before
    fun setUp() {
        startKoin {
            androidContext(InstrumentationRegistry.getInstrumentation().targetContext)
            modules(listOf(appModule, domainModule, dataModule))
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("com.example.testseekeritunes", appContext.packageName)
    }

    @Test
    fun suggest() {
    }

    @Test
    fun search() {
    }
}