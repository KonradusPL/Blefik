package com.konradpekala.blefik.data.model

data class Player(var id: String = "",
                  var nick: String = "",
                  var currentCards: ArrayList<Card> = ArrayList())