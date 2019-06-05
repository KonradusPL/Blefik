package com.konradpekala.blefik

import android.util.Log
import io.reactivex.Single
import org.junit.Test
import java.util.*
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
}