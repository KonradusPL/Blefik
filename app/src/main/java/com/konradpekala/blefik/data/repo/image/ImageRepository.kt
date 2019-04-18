package com.konradpekala.blefik.data.repo.image

import android.util.Log
import com.konradpekala.blefik.utils.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File

class ImageRepository(val remote: IImageRepository,
                      val local: IImageRepository) {
     fun getImageUrl(urlType: UrlType): String {
        return when (urlType){
             UrlType.REMOTE -> remote.getImageUrl()
             UrlType.LOCAL -> local.getImageUrl()
         }
    }

     fun saveImage(imagePath: String, id: String): Completable {
        return remote.saveImage(imagePath,id)/*.concatWith{
            local.saveImage(imagePath,id)
        }*/.subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }

     fun getProfileImage(id: String): Single<File> {
        Log.d("ImageRepository","getProfileImage")
        return remote.getProfileImage(id)
    }


} enum class UrlType{REMOTE,LOCAL}