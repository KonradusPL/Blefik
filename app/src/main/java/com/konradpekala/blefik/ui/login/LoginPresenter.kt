package com.konradpekala.blefik.ui.login

import android.util.Log
import com.konradpekala.blefik.data.model.User
import com.konradpekala.blefik.data.model.request.LoginRequest
import com.konradpekala.blefik.domain.usecase.SaveUserUseCase
import com.konradpekala.blefik.domain.usecase.SignInUseCase
import com.konradpekala.blefik.domain.usecase.SignUpUseCase

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

        val loginRequest = LoginRequest(email, password)

        view.showLoading()
        mSignUpUseCase.excecute(loginRequest, {
            val user = User(nick = nick, email = email, password = password)
            mSaveUserUseCase.excecute(user,{
                view.hideLoading()
                view.showMessage("Sukces!")
                view.openMainActivity()
            },{saveUserError: Throwable ->
                view.hideLoading()
                Log.e(TAG,saveUserError.toString())
            })
        },{ signUpError: Throwable ->
            view.hideLoading()
            Log.e(TAG,signUpError.toString())
        })
    }


    override fun onSignInButtonClick(email: String, password: String) {
        view.showLoading()

        val loginRequest = LoginRequest(email, password)
        mSignInUseCase.excecute(loginRequest, {
            val user = User(email = email, password = password)
            mSaveUserUseCase.excecute(user,{
                view.hideLoading()
                view.showMessage("Sukces!")
                view.openMainActivity()
            },{saveUserError: Throwable ->
                Log.e(TAG,saveUserError.toString())
            })
        },{t: Throwable ->

        })
    }

    fun dispose() {
        mSignInUseCase.dispose()
        mSaveUserUseCase.dispose()
        mSignUpUseCase.dispose()
    }

}