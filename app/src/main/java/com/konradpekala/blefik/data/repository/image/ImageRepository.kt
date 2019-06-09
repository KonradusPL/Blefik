package com.konradpekala.blefik.data.repository.image

import android.util.Log
import com.konradpekala.blefik.utils.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class ImageRepository @Inject constructor(@Named("ImageRepoRemote") val remote: IImageRepository,
                                          @Named("ImageRepoLocal") val local: IImageRepository) {

    private val TAG  = "ImageRepository"

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
        Log.d(TAG,"getProfileImage")
        return remote.getProfileImage(id)
    }

    fun clean(){
        remote.clean()
    }


} enum class UrlType{REMOTE,LOCAL}