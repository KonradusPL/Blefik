package com.konradpekala.blefik.data.model

import com.google.firebase.firestore.Exclude

class Room(
    var name: String = "",
    var usersCount: Int = 0,
    var creatorId: String = "",
    var removed: Boolean = false,
    @get:Exclude var isLoading: Boolean = false,
    @get:Exclude var roomId: String = "",
    var started: Boolean = false,
    val players: ArrayList<Player> = ArrayList()
                )