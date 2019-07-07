package com.konradpekala.blefik.data.model.user

import java.io.File

object UserBuilder {
    private var user: User = User()
    fun withUser(user: User): UserBuilder{
        this.user = user.copy()
        return this
    }
    fun withImageFile(imageFile: File): UserBuilder{
        user.image.file = imageFile
        return this
    }
    fun build(): User{
        return user
    }
}