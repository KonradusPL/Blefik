package com.konradpekala.blefik.injection.modules

import android.app.Application
import android.content.Context
import com.konradpekala.blefik.injection.other.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val mApplication: Application) {

    @Provides
    fun application() = mApplication

    @Provides
    fun provideContext(): Context = mApplication

}