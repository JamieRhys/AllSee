package com.sycosoft.allsee.di.modules

import com.sycosoft.allsee.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector
    abstract fun activity(): MainActivity
}