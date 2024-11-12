package com.sycosoft.allsee.di.modules

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestApplicationModule {
    @Provides
    @Singleton
    fun provideContext(): Context = ApplicationProvider.getApplicationContext()
}