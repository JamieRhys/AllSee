package com.sycosoft.allsee.di.modules

import android.app.Application
import android.content.Context
import com.sycosoft.allsee.data.local.CryptoManager
import dagger.Module
import dagger.Provides
import jakarta.inject.Singleton

@Module
class AppModule(private val application: Application) {
    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    fun provideContext(): Context = application.applicationContext
}