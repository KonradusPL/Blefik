package com.konradpekala.blefik

import com.konradpekala.blefik.data.model.Bid
import com.konradpekala.blefik.data.model.Card
import com.konradpekala.blefik.data.model.CardNumber
import com.konradpekala.blefik.utils.CardsStuff
import org.junit.Test

import org.junit.Assert.*

class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testAbsenceOfBid(){
        val bidAce = Bid(name = CardsStuff.wysokaKarta, firstCardNumber = CardNumber.Ace)
        val bidStrit = Bid(name = CardsStuff.strit,firstCardNumber = CardNumber.King)
        val cards = arrayListOf(
            Card(CardNumber.Nine),
            Card(CardNumber.King),
            Card(CardNumber.Queen),
            Card(CardNumber.Jack),
            Card(CardNumber.Ten))
        assertTrue(CardsStuff.isBidInCards(cards,bidStrit))
    }
}
