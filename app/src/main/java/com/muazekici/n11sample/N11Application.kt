package com.muazekici.n11sample

import android.app.Application
import com.muazekici.n11sample.di.AppComponent
import com.muazekici.n11sample.di.DaggerAppComponent
import timber.log.Timber

class N11Application : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        appComponent = DaggerAppComponent.factory().create(this,"https://api.github.com/")
        appComponent.injectApplication(this)
    }
}