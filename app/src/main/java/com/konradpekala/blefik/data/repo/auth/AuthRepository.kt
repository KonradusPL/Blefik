package com.konradpekala.blefik.data.repo.auth

import com.konradpekala.blefik.utils.SchedulerProvider
import io.reactivex.Single

interface AuthRepository {
    fun logOut()
}