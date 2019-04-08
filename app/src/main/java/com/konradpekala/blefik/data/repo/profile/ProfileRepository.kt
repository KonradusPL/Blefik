package com.konradpekala.blefik.data.repo.profile

import io.reactivex.Completable

interface ProfileRepository {
    fun saveImage()
    fun getImage()
    fun changeNick(newNick: String): Completable
}