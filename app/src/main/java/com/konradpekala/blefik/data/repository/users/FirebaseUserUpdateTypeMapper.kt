package com.konradpekala.blefik.data.repository.users

import com.konradpekala.blefik.utils.schedulers.ValueToUpdate
import javax.inject.Inject

class FirebaseUserUpdateTypeMapper @Inject constructor(): UserUpdateTypeMapper {
    override fun map(valueToUpdate: ValueToUpdate): String {
        return when(valueToUpdate){
            ValueToUpdate.EMAIL -> "email"
            ValueToUpdate.NICK -> "nick"
        }
    }
}