package com.konradpekala.blefik.data.model

data class Card(val number: CardNumber, val color: CardColor){

    fun fromNumberToString(): String{
        return when(number){
            CardNumber.Ace -> "A"
            CardNumber.King -> "K"
            CardNumber.Queen -> "Q"
            CardNumber.Jack -> "J"
            CardNumber.Ten -> "10"
            CardNumber.Nine -> "9"
        }
    }
}
enum class CardNumber{ Nine,Ten,Jack,Queen,King,Ace }
enum class CardColor{Club,Diamond,Heart,Spade}