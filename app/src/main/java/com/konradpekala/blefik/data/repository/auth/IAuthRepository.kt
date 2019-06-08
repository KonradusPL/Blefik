package com.konradpekala.blefik.data.repository.auth

interface IAuthRepository {
    fun logOut()
    fun getId(): String
}