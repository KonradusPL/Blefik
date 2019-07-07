package com.konradpekala.blefik.data.model

import com.google.firebase.firestore.Exclude
import java.io.File

data class Image(var url: String = "", @get:Exclude var file: File? = null){
    fun clear(){
        file?.delete()
    }
}