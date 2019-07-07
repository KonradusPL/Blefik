package com.konradpekala.blefik.utils.schedulers

import com.konradpekala.blefik.utils.SchedulerProvider
import dagger.Provides
import javax.inject.Inject

class OnSubscribeScheduler @Inject constructor() {
    val scheduler = SchedulerProvider.io()
}