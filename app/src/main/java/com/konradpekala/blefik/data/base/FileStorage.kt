package com.konradpekala.blefik.data.base

import io.reactivex.Completable
import io.reactivex.Single
import java.io.File

interface FileStorage {
    fun getFile(fileName: String): Single<File>
    fun saveFile(file: File, lastPathSegment: String): Completable
    fun getFilePath(): Single<String>
}