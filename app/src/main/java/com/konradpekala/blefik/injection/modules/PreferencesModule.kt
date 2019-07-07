package com.konradpekala.blefik.injection.modules

import com.konradpekala.blefik.data.preferences.Preferences
import com.konradpekala.blefik.data.preferences.SharedPrefs
import dagger.Binds
import dagger.Module

@Module
abstract class PreferencesModule {
    @Binds
    abstract fun bindPreferences(prefs: SharedPrefs): Preferences
}