package com.konradpekala.blefik

import com.konradpekala.blefik.data.model.Bid
import com.konradpekala.blefik.data.model.Card
import com.konradpekala.blefik.data.model.CardNumber
import com.konradpekala.blefik.utils.CardsUtils
import com.konradpekala.blefik.utils.SchedulerProvider
import io.reactivex.Completable
import org.junit.Test

import org.junit.Assert.*

class ExampleUnitTest {

    @Test
    fun testAbsenceOfBid(){
        val bidAce = Bid(name = CardsUtils.wysokaKarta, firstCardNumber = CardNumber.Ace)
        val bidStrit = Bid(name = CardsUtils.strit,firstCardNumber = CardNumber.King)
        val cards = arrayListOf(
            Card(CardNumber.Nine),
            Card(CardNumber.King),
            Card(CardNumber.Queen),
            Card(CardNumber.Jack),
            Card(CardNumber.Ten))
        assertTrue(CardsUtils.isBidInCards(cards,bidStrit))
    }

    fun printHelloRemotely(): Completable{
        Thread.sleep(3000)
        print("Hello world remotely!\n")
        return Completable.complete().subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.io())
    }

    fun printHelloWorldLocally(): Completable{
        Thread.sleep(3000)
        print("Hello world locally!\n")
        return Completable.complete().subscribeOn(SchedulerProvider.io())
    }

    @Test
    fun testCompletable(){
        printHelloRemotely()
            .concatWith(printHelloWorldLocally())
            .subscribe {
                print("completable item\n")
            }
    }
}
