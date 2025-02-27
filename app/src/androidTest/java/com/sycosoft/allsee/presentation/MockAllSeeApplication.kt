package com.sycosoft.allsee.presentation

import com.sycosoft.allsee.di.components.DaggerMockApplicationComponent
import com.sycosoft.allsee.di.components.MockApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class MockAllSeeApplication : AllSeeApplication() {

    lateinit var component: MockApplicationComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        component = DaggerMockApplicationComponent
            .factory()
            .create(application = this)
        return component
    }
}