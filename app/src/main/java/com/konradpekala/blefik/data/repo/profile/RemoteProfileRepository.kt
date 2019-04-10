package com.konradpekala.blefik.data.repo.profile

import android.graphics.Bitmap
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File

interface RemoteProfileRepository {
    fun saveImage(imagePath: String): Single<String>
    fun getProfileImage(): Single<File>
    fun changeNick(newNick: String): Completable
    fun getNick(): String
    fun getEmail(): String
}