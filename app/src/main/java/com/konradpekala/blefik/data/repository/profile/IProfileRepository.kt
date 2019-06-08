package com.konradpekala.blefik.data.repository.profile

import com.konradpekala.blefik.data.model.User
import io.reactivex.Completable

interface IProfileRepository {
    interface Remote{
        fun saveImageUrl(url: String): Completable
        fun setNick(newNick: String): Completable
        fun clean()
        fun saveProfile(profile: User): Completable
    }
    interface Local{
        fun setNick(newNick: String): Completable
        fun getNick(): String
        fun getEmail(): String
    }
}