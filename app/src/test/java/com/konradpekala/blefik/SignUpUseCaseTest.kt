package com.konradpekala.blefik

import android.content.Context
import com.google.firebase.FirebaseApp
import com.konradpekala.blefik.data.auth.FirebaseUserSession
import com.konradpekala.blefik.data.model.request.LoginRequest
import com.konradpekala.blefik.data.model.user.User
import com.konradpekala.blefik.data.preferences.SharedPrefs
import com.konradpekala.blefik.data.repository.users.FirebaseUserRepository
import com.konradpekala.blefik.data.repository.users.UserRepository
import com.konradpekala.blefik.data.storage.FirebaseStorage
import com.konradpekala.blefik.domain.interactors.user.SaveUserUseCase
import io.reactivex.Completable
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
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
        FirebaseApp.initializeApp(mockContext)
    }

    @Test
    fun processSigningUp(){

        val testScheduler = TestScheduler()

        val auth = FirebaseUserSession(mockAuth)
        val preferences = SharedPrefs(mockContext)

        `when`(auth.signUp(TEST_EMAIL,TEST_PASSWORD))
            .then{ Completable.complete()}

        /*val signUpUseCase = SignUpUseCase(
            testScheduler,
            testScheduler,
            userSession,
            preferences,
            SaveUserUseCase()
        )*/

        val loginRequest = LoginRequest(TEST_EMAIL,TEST_PASSWORD)

        //val testObserver = signUpUseCase.raw(loginRequest)
            //.test()

        //testObserver.awaitTerminalEvent()

       // testObserver.assertComplete()
    }

    @Test
    fun saveUserToDatabase(){
        val auth = FirebaseUserSession(mockAuth)
        val preferences = SharedPrefs(mockContext)
        val remoteUserRepository = FirebaseUserRepository(auth, FirebaseStorage())

        val userRepository = UserRepository(remoteUserRepository,preferences,auth)

        val testScheduler = TestScheduler()
        val saveUserUseCase = SaveUserUseCase(
            testScheduler,
            testScheduler,
            userRepository,
            preferences
        )

        val testUser = User("Test50","iljdilasdas","email","password")


        val response = saveUserUseCase.raw(testUser).blockingGet()
    }
}