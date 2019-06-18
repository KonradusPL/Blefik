package com.konradpekala.blefik.data.model.user

import com.konradpekala.blefik.data.model.Image

data class User(var nick: String = "",
                var id: String = "",
                var email: String = "",
                var password: String = "",
                var gamesWon: Int = 0,
                var image: Image = Image()
)
