package com.konradpekala.blefik

import com.konradpekala.blefik.data.auth.FirebaseAuth
import com.konradpekala.blefik.data.repository.users.FirebaseUserRepository
import com.konradpekala.blefik.data.storage.FirebaseStorage
import org.junit.Test

class FirestoreTest {

    val firebaseProfileRepository = FirebaseUserRepository(FirebaseAuth(),FirebaseStorage)

    @Test fun testSetImageUrl(){
        val profileRepository = FirebaseUserRepository(FirebaseAuth(),FirebaseStorage)
        profileRepository.saveImageUrl("").subscribe({
            print("success")
        },{t: Throwable? ->
            print(t.toString())
        })
    }
}