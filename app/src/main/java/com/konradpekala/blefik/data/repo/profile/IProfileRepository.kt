package com.konradpekala.blefik.data.repo.profile

import android.graphics.Bitmap
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File

interface IProfileRepository {
    fun saveImageUrl(url: String): Completable
    fun setNick(newNick: String): Completable
    fun getNick(): String
    fun getEmail(): String
    fun clean()
}