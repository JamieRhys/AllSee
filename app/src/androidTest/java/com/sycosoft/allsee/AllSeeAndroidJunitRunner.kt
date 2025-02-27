package com.sycosoft.allsee

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.sycosoft.allsee.presentation.MockAllSeeApplication

class AllSeeAndroidJunitRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application? {
        return super.newApplication(cl, MockAllSeeApplication::class.java.name, context)
    }
}