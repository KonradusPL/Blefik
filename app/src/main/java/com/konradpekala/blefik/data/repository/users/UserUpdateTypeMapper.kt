package com.konradpekala.blefik.data.repository.users

import com.konradpekala.blefik.utils.schedulers.ValueToUpdate

interface UserUpdateTypeMapper {
    fun map(valueToUpdate: ValueToUpdate): String
}