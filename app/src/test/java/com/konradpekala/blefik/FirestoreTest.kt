package com.konradpekala.blefik

import com.konradpekala.blefik.data.auth.FirebaseAuth
import com.konradpekala.blefik.data.repository.profile.FirebaseProfileRepository
import com.konradpekala.blefik.data.storage.FirebaseStorage
import org.junit.Test

class FirestoreTest {

    val firebaseProfileRepository = FirebaseProfileRepository(FirebaseAuth(),FirebaseStorage)

    @Test fun testSetImageUrl(){
        val profileRepository = FirebaseProfileRepository(FirebaseAuth(),FirebaseStorage)
        profileRepository.saveImageUrl("").subscribe({
            print("success")
        },{t: Throwable? ->
            print(t.toString())
        })
    }
}