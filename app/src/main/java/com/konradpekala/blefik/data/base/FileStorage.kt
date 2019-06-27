package com.konradpekala.blefik.data.base

import io.reactivex.Completable
import io.reactivex.Single
import java.io.File

interface FileStorage {
    fun downloadFile(fileName: String): Single<File>
    fun uploadFile(file: File, lastPathSegment: String): Completable
    fun getFilePath(): Single<String>
}