package com.konradpekala.blefik.ui

import android.app.Activity
import android.app.Application
import androidx.annotation.UiThread
import com.konradpekala.blefik.injection.component.ApplicationComponent
import com.konradpekala.blefik.injection.component.DaggerApplicationComponent
import com.konradpekala.blefik.injection.modules.ApplicationModule
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class BlefikApplication: Application(), HasActivityInjector {
    private var mAppComponent: ApplicationComponent? = null

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }
}