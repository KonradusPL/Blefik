package com.konradpekala.blefik.data.model

data class Card(var number: CardNumber = CardNumber.None,
                var color: CardColor = CardColor.None){

    fun fromNumberToString(): String{
        return when(number){
            CardNumber.Ace -> "A"
            CardNumber.King -> "K"
            CardNumber.Queen -> "Q"
            CardNumber.Jack -> "J"
            CardNumber.Ten -> "10"
            CardNumber.Nine -> "9"
            CardNumber.None -> ""
        }
    }
    fun map(): HashMap<String, Any>{
        val map = HashMap<String,Any>()
        map["number"] = number.name
        map["color"] = color.name
        return map
    }
    companion object {
        fun fromStringToNumber(text: String): CardNumber{
            return when(text){
                "A" -> CardNumber.Ace
                "K" -> CardNumber.King
                "Q" -> CardNumber.Queen
                "J" -> CardNumber.Jack
                "10" -> CardNumber.Ten
                "9" -> CardNumber.Nine
                ""  -> CardNumber.None
                else -> CardNumber.None
            }
        }
    }

}
enum class CardNumber{ Nine,Ten,Jack,Queen,King,Ace,None }
enum class CardColor{Club,Diamond,Heart,Spade,None}