package com.konradpekala.blefik.utils

import com.konradpekala.blefik.data.model.*
import java.util.*
import kotlin.collections.ArrayList

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

    fun generateBidTypesForCreator(): ArrayList<BidType>{
        return arrayListOf(
            BidType("Wysoka karta",BidPickingType.OneCard,1, arrayListOf(BidValue())),
            BidType("Para",BidPickingType.OneCard,2, arrayListOf(BidValue())),
            BidType("Dwie pary",BidPickingType.TwoCards,3, arrayListOf(BidValue())),
            BidType("Trójka",BidPickingType.OneCard,4, arrayListOf(BidValue())),
            BidType("Strit",BidPickingType.Set,5, arrayListOf(BidValue())),
            BidType("Kolor",BidPickingType.Color,6, arrayListOf(BidValue())),
            BidType("Full",BidPickingType.TwoCards,7, arrayListOf(BidValue())),
            BidType("Kareta",BidPickingType.OneCard,8, arrayListOf(BidValue())),
            BidType("Poker",BidPickingType.Color,9, arrayListOf(BidValue())),
            BidType("Poker królewski",BidPickingType.Color,10, arrayListOf(BidValue())))
    }

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

    fun generateCardsForBid(bid: Bid?): ArrayList<Card>{
        val bidCards = ArrayList<Card>()
        if (bid == null)
            return bidCards

        val bidName = bid.name

        when(bidName){
            "Wysoka karta" ->{
                bidCards.add(Card(bid.firstCardNumber))
            }
            "Para" ->{
                bidCards.add(Card(bid.firstCardNumber))
                bidCards.add(Card(bid.firstCardNumber))
            }
            "Dwie pary" ->{
                bidCards.add(Card(bid.firstCardNumber))
                bidCards.add(Card(bid.firstCardNumber))
                bidCards.add(Card(bid.secondCardNumber))
                bidCards.add(Card(bid.secondCardNumber))
            }
            "Trójka" ->{
                bidCards.add(Card(bid.firstCardNumber))
                bidCards.add(Card(bid.firstCardNumber))
                bidCards.add(Card(bid.firstCardNumber))
            }
            "Strit" ->{
                val ace = Card(CardNumber.Ace)
                bidCards.add(ace)
                bidCards.add(Card(CardNumber.King))
                bidCards.add(Card(CardNumber.Queen))
                bidCards.add(Card(CardNumber.Jack))
                bidCards.add(Card(CardNumber.Ten))
                if(bid.firstCardNumber == CardNumber.King){
                    bidCards.remove(ace)
                    bidCards.add(Card(CardNumber.Nine))
                }
            }
            "Kolor" ->{
                for(i in 1..5)
                    bidCards.add(Card(CardNumber.None,bid.color))
            }
            "Full" ->{
                bidCards.add(Card(bid.firstCardNumber))
                bidCards.add(Card(bid.firstCardNumber))
                bidCards.add(Card(bid.firstCardNumber))
                bidCards.add(Card(bid.firstCardNumber))
            }
            "Kareta" ->{
                bidCards.add(Card(bid.firstCardNumber))
                bidCards.add(Card(bid.firstCardNumber))
                bidCards.add(Card(bid.firstCardNumber))
                bidCards.add(Card(bid.secondCardNumber))
                bidCards.add(Card(bid.secondCardNumber))
            }
            "Poker" ->{
                bidCards.add(Card(CardNumber.King))
                bidCards.add(Card(CardNumber.Queen))
                bidCards.add(Card(CardNumber.Jack))
                bidCards.add(Card(CardNumber.Ten))
                bidCards.add(Card(CardNumber.Nine))
            }
            "Poker królewski" ->{
                bidCards.add(Card(CardNumber.Ace,bid.color))
                bidCards.add(Card(CardNumber.King,bid.color))
                bidCards.add(Card(CardNumber.Queen,bid.color))
                bidCards.add(Card(CardNumber.Jack,bid.color))
                bidCards.add(Card(CardNumber.Ten,bid.color))
            }
        }
        return bidCards
    }
}