package com.konradpekala.blefik.injection.modules

import android.app.Application
import android.content.Context
import com.konradpekala.blefik.ui.login.LoginActivity
import com.konradpekala.blefik.ui.login.LoginMvp
import com.konradpekala.blefik.ui.main.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector()
    abstract fun contributeLoginActivity(): LoginActivity

    @ContributesAndroidInjector()
    abstract fun contributeMainActivity(): MainActivity

    @Binds
    abstract fun bindContext(application: Application): Context

    @Binds
    abstract fun bindLoginView(loginActivity: LoginActivity): LoginMvp.View
}