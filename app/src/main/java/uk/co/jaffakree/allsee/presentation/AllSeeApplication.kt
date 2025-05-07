package uk.co.jaffakree.allsee.presentation

import uk.co.jaffakree.allsee.di.components.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class AllSeeApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerApplicationComponent
            .factory()
            .create(application = this)

}