package com.konradpekala.blefik.data.model

data class Player(var id: String = "",
                  var nick: String = "",
                  var currentCards: ArrayList<Card> = ArrayList(),
                  var cardsCount: Int = 0){
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