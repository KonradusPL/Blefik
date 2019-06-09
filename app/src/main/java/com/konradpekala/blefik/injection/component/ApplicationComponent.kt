package com.konradpekala.blefik.injection.component

import android.app.Application
import com.konradpekala.blefik.injection.modules.*
import com.konradpekala.blefik.injection.other.ApplicationScope
import com.konradpekala.blefik.ui.BlefikApplication
import com.konradpekala.blefik.ui.login.LoginActivity
import com.konradpekala.blefik.ui.main.MainActivity
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [
    ActivityModule::class,
    FragmentModule::class,
    AuthModule::class,
    PreferencesModule::class,
    RepositoryModule::class
])
@Singleton
interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }

    fun inject(loginActivity: LoginActivity)
    fun inject(mainActivity: MainActivity)
    fun inject(blefikApplication: BlefikApplication)

}