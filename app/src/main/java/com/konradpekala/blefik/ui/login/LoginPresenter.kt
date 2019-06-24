package com.konradpekala.blefik.ui.login

import android.util.Log
import com.konradpekala.blefik.data.model.user.User
import com.konradpekala.blefik.data.model.request.LoginRequest
import com.konradpekala.blefik.data.model.request.RegisterRequest
import com.konradpekala.blefik.domain.interactors.SaveUserUseCase
import com.konradpekala.blefik.domain.interactors.SignInUseCase
import com.konradpekala.blefik.domain.interactors.SignUpUseCase

import javax.inject.Inject

class LoginPresenter@Inject constructor(private val mSignInUseCase: SignInUseCase,
                                        private val mSignUpUseCase: SignUpUseCase,
                                        private val mSaveUserUseCase: SaveUserUseCase
): LoginMvp.Presenter {

    private lateinit var view: LoginMvp.View

    val TAG = "LoginPresenter"

    fun onAttach(view: LoginMvp.View){
        this.view = view
    }

    override fun onSignUpButtonClick(email: String, password: String, nick: String) {
        if (password.length < 6){
            view.showMessage("Hasło jest za krótkie!")
            return
        }

        if (nick.length < 2){
            view.showMessage("Nick jest za krótki!")
            return
        }

        val registerRequest = RegisterRequest(email, password, nick)
        view.showLoading()

        mSignUpUseCase.excecute(registerRequest, {
            view.hideLoading()
            view.showMessage("Sukces!")
            view.openMainActivity()
        },{ signUpError: Throwable ->
            view.hideLoading()
            Log.d(TAG,signUpError.toString())
        })
    }


    override fun onSignInButtonClick(email: String, password: String) {
        view.showLoading()

        val loginRequest = LoginRequest(email, password)
        mSignInUseCase.excecute(loginRequest, {
            view.hideLoading()
            view.showMessage("Sukces!")
            view.openMainActivity()
        },{signInError: Throwable ->
            view.hideLoading()
            view.showMessage("Błędne dane logowania!")
            Log.d(TAG,signInError.toString())
        })
    }

    fun dispose() {
        mSignInUseCase.dispose()
        mSaveUserUseCase.dispose()
        mSignUpUseCase.dispose()
    }

}