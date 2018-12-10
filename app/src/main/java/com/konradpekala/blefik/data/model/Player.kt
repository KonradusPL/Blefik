package com.konradpekala.blefik.data.model

data class Player(var id: String = "",
                  var nick: String = "",
                  var cardsCount: Int = 0,
                  var currentCards: ArrayList<Card> = ArrayList())