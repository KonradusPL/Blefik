package com.konradpekala.blefik.data.repo.profile

import android.content.Context
import com.konradpekala.blefik.data.repo.image.IImageRepository
import io.reactivex.Single
import java.io.File
import android.content.ContextWrapper
import android.util.Log
import io.reactivex.Completable
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.channels.FileChannel


class LocalImageRepository(val ctx: Context): IImageRepository {

    var localImage: File? = null
    private var mImagePath: String? = null

    override fun getImageUrl(): String = mImagePath ?: ""

    override fun clean() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getProfileImage(id: String): Single<File> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveImage(imagePath: String, id: String): Completable {
        val cw = ContextWrapper(ctx)

        val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
        // Create imageDir
        val sourceFile = File(imagePath)
        val destFile = File(directory, "profile.jpg")

        copyFile(sourceFile,destFile)

        return Completable.complete()
    }

    private fun copyFile(sourceFile: File, destFile: File) {
        if (!destFile.parentFile.exists())
            destFile.parentFile.mkdirs()

        if (!destFile.exists()) {
            destFile.createNewFile()
        }

        var source: FileChannel? = null
        var destination: FileChannel? = null

        try {
            source = FileInputStream(sourceFile).channel
            destination = FileOutputStream(destFile).channel
            destination!!.transferFrom(source, 0, source!!.size())

            mImagePath = destFile.toString()

            Log.d("copyFile",destFile.toString())
            Log.d("copyFile",sourceFile.toString())
        } finally {
            source?.close()

            destination?.close()
        }
    }
}