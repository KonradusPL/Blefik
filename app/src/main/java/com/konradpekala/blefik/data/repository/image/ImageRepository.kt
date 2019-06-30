package com.konradpekala.blefik.data.repository.image

import com.konradpekala.blefik.data.base.FileStorage
import com.konradpekala.blefik.data.model.Image
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class ImageRepository @Inject constructor(@Named("ImageRepoRemote") private val mRemoteFileStorage: FileStorage) {

    private val TAG  = "ImageRepository"

    private var mCachedImage: Image? = null

    fun getImageUrl(urlType: UrlType): Single<String> {
        return when (urlType){
             UrlType.REMOTE -> mRemoteFileStorage.getFilePath()
             UrlType.LOCAL -> Single.error(Throwable("local saving not supported yet"))
         }
    }

     fun uploadImageWithUserIdAsName(imagePath: String, id: String): Completable {
         val file = File(imagePath)
         return mRemoteFileStorage.uploadFile(file,id)
    }

     fun getImageFile(name: String): Single<File> {
         mCachedImage?.let {
             return Single.just(it.file)
         }
         return mRemoteFileStorage.downloadFile(name)
            .doOnSuccess { imageFile: File? -> mCachedImage?.file = imageFile }
    }

    fun cleanCache(){
        mCachedImage?.clear()
        mCachedImage = null
    }


} enum class UrlType{REMOTE,LOCAL}