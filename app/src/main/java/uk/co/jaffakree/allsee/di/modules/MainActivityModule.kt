package uk.co.jaffakree.allsee.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import uk.co.jaffakree.allsee.presentation.MainActivity

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector
    abstract fun activity(): MainActivity
}