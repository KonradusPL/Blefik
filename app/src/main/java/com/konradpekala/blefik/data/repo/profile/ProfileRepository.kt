package com.konradpekala.blefik.data.repo.profile

import android.graphics.Bitmap
import io.reactivex.Completable
import io.reactivex.Single

interface ProfileRepository {
    fun saveImage(imageBitmap: Bitmap): Single<String>
    fun getImage(url: String): Single<Bitmap>
    fun changeNick(newNick: String): Completable
    fun getNick(): String
    fun getEmail(): String
}