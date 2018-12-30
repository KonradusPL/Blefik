package com.konradpekala.blefik.utils

import com.konradpekala.blefik.data.model.*
import java.util.*
import kotlin.collections.ArrayList

object CardsStuff {

    var maxCardNumber = 4

    val wysokaKarta = "Wysoka karta"
    val para = "Para"
    val dwiePary = "Dwie pary"
    val trojka = "Trójka"
    val strit = "Strit"
    val kolor = "Kolor"
    val full = "Full"
    val kareta = "Kareta"
    val poker = "Poker"
    val pokerKrolewski = "Poker królewski"

    fun allCards() = arrayListOf(
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
            BidType(wysokaKarta,BidPickingType.OneCard,1, arrayListOf(BidValue())),
            BidType(para,BidPickingType.OneCard,2, arrayListOf(BidValue())),
            BidType(dwiePary,BidPickingType.TwoCards,3, arrayListOf(BidValue())),
            BidType(trojka,BidPickingType.OneCard,4, arrayListOf(BidValue())),
            BidType(strit,BidPickingType.Set,5, arrayListOf(BidValue())),
            BidType(kolor,BidPickingType.Color,6, arrayListOf(BidValue())),
            BidType(full,BidPickingType.TwoCards,7, arrayListOf(BidValue())),
            BidType(kareta,BidPickingType.OneCard,8, arrayListOf(BidValue())),
            BidType(poker,BidPickingType.Color,9, arrayListOf(BidValue())),
            BidType(pokerKrolewski,BidPickingType.Color,10, arrayListOf(BidValue())))
    }

    fun cardsForNewRound(players: ArrayList<Player>, firstRound: Boolean){
        val temporaryArray = ArrayList(allCards())
        val random = Random()

        for(player in players){
            if (firstRound)
                player.cardsCount++
            player.currentCards.clear()
            for(i in 1..player.cardsCount){
                val card = temporaryArray[random.nextInt(temporaryArray.size-1)]
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
            wysokaKarta ->{
                bidCards.add(Card(bid.firstCardNumber))
            }
            para ->{
                bidCards.add(Card(bid.firstCardNumber))
                bidCards.add(Card(bid.firstCardNumber))
            }
            dwiePary ->{
                bidCards.add(Card(bid.firstCardNumber))
                bidCards.add(Card(bid.firstCardNumber))
                bidCards.add(Card(bid.secondCardNumber))
                bidCards.add(Card(bid.secondCardNumber))
            }
            trojka ->{
                bidCards.add(Card(bid.firstCardNumber))
                bidCards.add(Card(bid.firstCardNumber))
                bidCards.add(Card(bid.firstCardNumber))
            }
            strit ->{
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
            kolor ->{
                for(i in 1..5)
                    bidCards.add(Card(CardNumber.None,bid.color))
            }
            full ->{
                bidCards.add(Card(bid.firstCardNumber))
                bidCards.add(Card(bid.firstCardNumber))
                bidCards.add(Card(bid.firstCardNumber))
                bidCards.add(Card(bid.secondCardNumber))
                bidCards.add(Card(bid.secondCardNumber))

            }
            kareta ->{
                bidCards.add(Card(bid.firstCardNumber))
                bidCards.add(Card(bid.firstCardNumber))
                bidCards.add(Card(bid.firstCardNumber))
                bidCards.add(Card(bid.firstCardNumber))
            }
            poker ->{
                bidCards.add(Card(CardNumber.King))
                bidCards.add(Card(CardNumber.Queen))
                bidCards.add(Card(CardNumber.Jack))
                bidCards.add(Card(CardNumber.Ten))
                bidCards.add(Card(CardNumber.Nine))
            }
            pokerKrolewski ->{
                bidCards.add(Card(CardNumber.Ace,bid.color))
                bidCards.add(Card(CardNumber.King,bid.color))
                bidCards.add(Card(CardNumber.Queen,bid.color))
                bidCards.add(Card(CardNumber.Jack,bid.color))
                bidCards.add(Card(CardNumber.Ten,bid.color))
            }
        }
        return bidCards
    }

    fun isBidInCards(cards: ArrayList<Card>,bid: Bid): Boolean{
        val bidCards = generateCardsForBid(bid)
        if(bidCards.size == 0){
            throw Throwable("bład przy tworzeniu kart ze stawki, bidCards.size == 0")
        }

        when(bid.name){
            wysokaKarta ->findOneTypeCard(cards,bidCards,1)
            para ->findOneTypeCard(cards,bidCards,2)
            dwiePary ->{
                var f = 0
                var s= 0
                for(card in cards){
                    if(card.number == bidCards[0].number)
                        f++
                    if(card.number == bidCards[3].number)
                        s++
                }
                if(f>=2 && s >= 2)
                    return true
            }
            trojka ->findOneTypeCard(cards,bidCards,3)
            strit ->{
                for (card in cards){
                    for(bidCard in bidCards){
                        if(bidCard.number == card.number){
                            bidCards.remove(bidCard)
                        }
                    }
                }
                if(bidCards.size == 0)
                    return true
            }
            kolor ->{
                var c = 0
                for(card in cards){
                    if(card.color == bid.color)
                        c++
                }
                if(c >= 5)
                    return true
            }
            full ->{
                var f = 0
                var s= 0
                for(card in cards){
                    if(card.number == bidCards[0].number)
                        f++
                    if(card.number == bidCards[3].number)
                        s++
                }
                if(f>=3 && s >= 2)
                    return true
            }
            kareta -> findOneTypeCard(cards,bidCards,4)
            poker -> return findPoker(cards, bidCards)
            pokerKrolewski -> return findPoker(cards,bidCards)
        }
        return false
    }

    private fun findPoker(cards: ArrayList<Card>, bidCards: ArrayList<Card>): Boolean{
        for(card in cards){
            var cardFound = false
            for(bidCard in bidCards){
                if(bidCard.color == card.color && bidCard.number == card.number)
                    cardFound = true
            }
            if (!cardFound)
                return false
        }
        return true
    }
    private fun findOneTypeCard(cards: ArrayList<Card>, bidCards: ArrayList<Card>, n: Int): Boolean{
        var f = 0
        for(card in cards) {
            if (card.number == bidCards[0].number)
                f++
        }
        return f>=n
    }
}