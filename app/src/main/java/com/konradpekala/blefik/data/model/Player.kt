package com.konradpekala.blefik.data.model

import com.google.firebase.firestore.Exclude
import java.util.Comparator

data class Player(var id: String = "",
                  var nick: String = "",
                  var currentCards: ArrayList<Card> = ArrayList(),
                  var cardsCount: Int = 0,
                  @get:Exclude var isCurrentPlayer: Boolean = false){
    fun map(): HashMap<String,Any>{
        val map = HashMap<String,Any>()
        map["id"] = id
        map["nick"] = nick
        map["currentCards"] = mapCards()
        map["cardsCount"] = cardsCount
        return map
    }

    fun mapCards(): ArrayList<HashMap<String, Any>>{
        val arrayList = ArrayList<HashMap<String,Any>>()
        for(card in currentCards){
            arrayList.add(card.map())
        }
        return arrayList
    }
}
class SortByNick(): Comparator<Player>{
    override fun compare(player1: Player?, player2: Player?): Int {
        val c1 = player1?.nick?.get(0) ?: ' '
        val c2 = player2?.nick?.get(0) ?: ' '
        return c1 - c2
    }

}