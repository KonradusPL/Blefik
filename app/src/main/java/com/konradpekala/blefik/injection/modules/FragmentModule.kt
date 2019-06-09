package com.konradpekala.blefik.injection.modules

import com.konradpekala.blefik.ui.login.LoginActivity
import com.konradpekala.blefik.ui.main.ranking.RankingFragment
import com.konradpekala.blefik.ui.main.rooms.RoomsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector()
    abstract fun contributeRoomFragment(): RoomsFragment

    @ContributesAndroidInjector()
    abstract fun contributeRankingFragment(): RankingFragment
}