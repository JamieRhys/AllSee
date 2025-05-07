package uk.co.jaffakree.allsee.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ContextModule {
    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context = application.applicationContext
}