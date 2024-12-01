package com.sycosoft.allsee.di.modules

import android.content.Context
import androidx.navigation.NavHostController
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NavigationModule {
    @Provides
    @Singleton
    fun provideNavController(context: Context): NavHostController = NavHostController(context = context)
}