package com.konradpekala.blefik

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Single
import org.jetbrains.anko.doAsync
import org.junit.Test
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.stream.IntStream

class RxTest {

    @Test fun testSubscribeMethod(){
        val single = Single.create<Int> { emitter ->
            val random = Random()
            val number = random.nextInt(1000)
            emitter.onSuccess(number)
        }

        single.subscribe { t: Int ->
            println(t)
        }
        single.subscribe { t: Int ->
            println(t)
        }
        single.subscribe { t: Int ->
            println(t)
        }
    }

    @Test fun testDoOnComplete(){
        val completable1 = Completable.timer(5,TimeUnit.SECONDS)

        val completable2 = Completable.complete()
        .doOnComplete { println("completable2: doOnComplete") }

        completable1.doOnComplete { println("completable1: doOnComplete") }
            .doOnComplete {  doSomething()}
            .andThen(completable2)
            .blockingGet()

    }

    @Test
    fun testAndThen(){
        val signUp = Completable.create { emitter ->
            doAsync {
                Thread.sleep(1000)
                println("onComplete Sign Up")
                emitter.onComplete()
            }
        }

        Thread.sleep(4000)

        signUp.doOnComplete { println("doOnComplete SignUp") }
            .andThen(Completable.defer { s1() })
            .subscribe({
                println("subscribe")
            },{t: Throwable? ->
                println("error: $t")
            })
        Thread.sleep(6000)
    }

    private fun s1(): Completable{
        println("s1")
        return Completable.create { emitter ->
            doAsync {
                Thread.sleep(2000)
                println("onComplete Save User")
                //emitter.onComplete()
            }
        }
    }

    private fun doSomething(){
        println("after completable 1")
    }
}