package com.konradpekala.blefik.data.repository.profile

import com.konradpekala.blefik.data.model.User
import io.reactivex.Completable
import io.reactivex.Single

interface IProfileRepository {
    interface Remote{
        fun saveImageUrl(url: String): Completable
        fun setNick(newNick: String): Completable
        fun getNick(id: String): Single<String>
        fun clean()
        fun saveProfile(profile: User): Completable
    }
    interface Local{
        fun setNick(newNick: String): Completable
        fun getNick(): String
        fun getEmail(): String
    }
}