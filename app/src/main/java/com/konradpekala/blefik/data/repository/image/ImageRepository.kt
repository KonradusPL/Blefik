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
class ImageRepository @Inject constructor(@Named("ImageRepoRemote") val remote: FileStorage,
                                          @Named("ImageRepoLocal") val local: IImageRepository) {

    private val TAG  = "ImageRepository"

    private var mCachedImage: Image? = null

    fun getImageUrl(urlType: UrlType): String {
        return when (urlType){
             UrlType.REMOTE -> remote.getFilePath().blockingGet()
             UrlType.LOCAL -> "error"
         }
    }

     fun saveImage(imagePath: String, id: String): Completable {
         val file = File(imagePath)
         return remote.saveFile(file,id)
    }

     fun getImageFile(name: String): Single<File> {
         mCachedImage?.let {
             return Single.just(it.file)
         }
         return remote.getFile(name)
            .doOnSuccess { imageFile: File? -> mCachedImage?.file = imageFile }
    }

    fun clean(){
        mCachedImage?.clear()
        mCachedImage = null
        //remote.clean()
    }


} enum class UrlType{REMOTE,LOCAL}