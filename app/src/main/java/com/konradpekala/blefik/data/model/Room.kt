package com.konradpekala.blefik.data.model

data class Room(
    var name: String = "",
    var usersCount: Int = 0,
    var creatorId: String = "",
    var isRemoved: Boolean = false
                )