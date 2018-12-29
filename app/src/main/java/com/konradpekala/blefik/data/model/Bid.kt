package com.konradpekala.blefik.data.model

data class Bid(var name: String = "",
               var power: Int = 0,
               var pickingType: BidPickingType = BidPickingType.OneCard,
               var color: CardColor = CardColor.None,
               var firstCardNumber: CardNumber = CardNumber.None,
               var secondCardNumber: CardNumber = CardNumber.None) {

    fun map(): HashMap<String,Any>{
        val hashMap = HashMap<String,Any>()
        hashMap["name"] = name
        hashMap["power"] = power
        hashMap["pickingType"] = pickingType.name
        hashMap["color"] = color.name
        hashMap["firstCardNumber"] = firstCardNumber.name
        hashMap["secondCardNumber"] = secondCardNumber.name
        return hashMap
    }
}