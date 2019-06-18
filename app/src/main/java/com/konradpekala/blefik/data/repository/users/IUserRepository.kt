package com.konradpekala.blefik.data.repository.users

import com.konradpekala.blefik.data.model.user.User
import io.reactivex.Completable
import io.reactivex.Single

interface IUserRepository {
    interface Remote{
        fun saveImageUrl(url: String): Completable
        fun setNick(newNick: String): Completable
        fun getNick(id: String): Single<String>
        fun clean()
        fun saveUser(user: User): Completable
        fun getAllUsers(): Single<List<User>>
        fun getUser(id: String): Single<User>
    }
}