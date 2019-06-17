package com.konradpekala.blefik

import android.content.Context
import com.google.firebase.FirebaseApp
import com.konradpekala.blefik.data.auth.FirebaseAuth
import com.konradpekala.blefik.data.model.request.LoginRequest
import com.konradpekala.blefik.data.preferences.SharedPrefs
import com.konradpekala.blefik.domain.interactors.SignUpUseCase
import com.konradpekala.blefik.utils.SchedulerProvider
import com.konradpekala.blefik.utils.schedulers.OnObserveScheduler
import com.konradpekala.blefik.utils.schedulers.OnSubscribeScheduler
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.assertj.core.api.Assertions.assertThat
import org.mockito.runners.MockitoJUnitRunner
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.junit.Rule
import org.mockito.Mockito.`when`


@RunWith(MockitoJUnitRunner::class)
class SignUpUseCaseTest {

    @get:Rule
    var rule = MockitoJUnit.rule()

    @Mock
    lateinit var mockContext: Context

    @Mock
    lateinit var mockAuth: com.google.firebase.auth.FirebaseAuth

    val TEST_EMAIL = "testMocked1@test.pl"
    val TEST_PASSWORD = "testMocked1"

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun processSigningUp(){

        val testScheduler = TestScheduler()

        val auth = FirebaseAuth(mockAuth)
        val preferences = SharedPrefs(mockContext)

        `when`(auth.signUp(TEST_EMAIL,TEST_PASSWORD))
            .then{ Completable.complete()}

        val signUpUseCase = SignUpUseCase(
            testScheduler,
            testScheduler,
            auth,
            preferences)

        val loginRequest = LoginRequest(TEST_EMAIL,TEST_PASSWORD)

        val testObserver = signUpUseCase.raw(loginRequest)
            .test()

        testObserver.awaitTerminalEvent()

        testObserver.assertComplete()


    }
}