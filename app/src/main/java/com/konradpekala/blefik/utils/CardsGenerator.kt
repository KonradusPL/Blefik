package com.konradpekala.blefik.utils

import com.konradpekala.blefik.data.model.Card
import com.konradpekala.blefik.data.model.CardColor
import com.konradpekala.blefik.data.model.CardNumber
import com.konradpekala.blefik.data.model.Player
import java.util.*

object CardsGenerator {

    val cards = arrayListOf(
        Card(CardNumber.Ace,CardColor.Club),
        Card(CardNumber.Ace,CardColor.Diamond),
        Card(CardNumber.Ace,CardColor.Spade),
        Card(CardNumber.Ace,CardColor.Heart),

        Card(CardNumber.King,CardColor.Club),
        Card(CardNumber.King,CardColor.Diamond),
        Card(CardNumber.King,CardColor.Spade),
        Card(CardNumber.King,CardColor.Heart),

        Card(CardNumber.Queen,CardColor.Club),
        Card(CardNumber.Queen,CardColor.Diamond),
        Card(CardNumber.Queen,CardColor.Spade),
        Card(CardNumber.Queen,CardColor.Heart),

        Card(CardNumber.Jack,CardColor.Club),
        Card(CardNumber.Jack,CardColor.Diamond),
        Card(CardNumber.Jack,CardColor.Spade),
        Card(CardNumber.Jack,CardColor.Heart),

        Card(CardNumber.Ten,CardColor.Club),
        Card(CardNumber.Ten,CardColor.Diamond),
        Card(CardNumber.Ten,CardColor.Spade),
        Card(CardNumber.Ten,CardColor.Heart),

        Card(CardNumber.Nine,CardColor.Club),
        Card(CardNumber.Nine,CardColor.Diamond),
        Card(CardNumber.Nine,CardColor.Spade),
        Card(CardNumber.Nine,CardColor.Heart)
        )

    fun cardsForNewRound(players: ArrayList<Player>, newRound: Boolean){
        val temporaryArray = ArrayList(cards)
        val random = Random()

        for(player in players){
            if (newRound)
                player.cardsCount++
            player.currentCards.clear()
            for(i in 1..player.cardsCount){
                val card = temporaryArray[random.nextInt(23)]
                temporaryArray.remove(card)
                player.currentCards.add(card)
            }
        }
    }
}