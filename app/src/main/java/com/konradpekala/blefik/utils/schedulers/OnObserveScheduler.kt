package com.konradpekala.blefik.utils.schedulers

import com.konradpekala.blefik.utils.SchedulerProvider
import javax.inject.Inject

class OnObserveScheduler @Inject constructor() {
    val scheduler = SchedulerProvider.ui()
}