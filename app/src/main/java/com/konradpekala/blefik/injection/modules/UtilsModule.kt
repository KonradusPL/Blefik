package com.konradpekala.blefik.injection.modules

import com.konradpekala.blefik.utils.SchedulerProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import javax.inject.Named

@Module
class UtilsModule {

    @Named("onObserve")
    @Provides
    fun provideObserveScheduler(): Scheduler = SchedulerProvider.ui()

    @Named("onSubscribe")
    @Provides
    fun provideSubscribeScheduler(): Scheduler = SchedulerProvider.io()
}