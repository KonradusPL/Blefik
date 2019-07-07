package com.konradpekala.blefik.ui

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseApp
import com.konradpekala.blefik.injection.component.ApplicationComponent
import com.konradpekala.blefik.injection.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class BlefikApplication: Application(), HasActivityInjector,HasSupportFragmentInjector {
    private var mAppComponent: ApplicationComponent? = null

    @Inject
    lateinit var mDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var mDispatchingFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return mDispatchingAndroidInjector
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return mDispatchingFragmentInjector
    }

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder()
            .application(this)
            .build()
            .inject(this)

        FirebaseApp.initializeApp(this)
    }
}