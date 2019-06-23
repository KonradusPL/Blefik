package com.konradpekala.blefik.data.repository.image

import io.reactivex.Completable
import io.reactivex.Single
import java.io.File

interface IImageRepository {
    fun getImage(id: String): Single<File>
    fun saveImage(imagePath: String, id: String): Completable
    fun getImageUrl(): String
    fun clean()
}