package com.sycosoft.allsee

import android.app.Application
import com.sycosoft.allsee.di.components.ApplicationComponent
import com.sycosoft.allsee.di.components.DaggerApplicationComponent
import com.sycosoft.allsee.di.modules.ContextModule

class AllSeeApplication : Application() {
    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerApplicationComponent
            .builder()
            .contextModule(ContextModule(this))
            .build()
    }

}