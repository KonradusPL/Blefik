package com.konradpekala.blefik.ui.login

import android.util.Log
import com.konradpekala.blefik.R
import com.konradpekala.blefik.data.repo.LoginRepository
import com.konradpekala.blefik.ui.base.BasePresenter

class LoginPresenter<V: LoginMvp.View>(view: V,val repo: LoginRepository): BasePresenter<V>(view),LoginMvp.Presenter<V> {

    override fun onSignUpButtonClick(email: String, password: String, nick: String) {
        if (password.length < 6){
            view.showMessage("Hasło jest za krótkie!")
            return
        }

        if (nick.length < 2){
            view.showMessage("Nick jest za krótki!")
            return
        }

        view.showLoading()
        cd.add(repo.signUp(email, password)
            .flatMap { id: String -> repo.createUser(email,password,id,nick) }
            .subscribe({
                repo.saveUserLocally(nick,email)
                view.hideLoading()
                view.showMessage("Sukces!")
                view.openMainActivity()
            },{t: Throwable? ->
                Log.d("onSignUpButtonClick",t.toString())
                view.hideLoading()
                view.showMessage("Nie udało się!")
                view.showMessage(t.toString())
            }))
    }

    override fun onSignInButtonClick(email: String, password: String) {
        view.showLoading()
        cd.add(repo.signIn(email, password)
            .flatMap { id: String -> repo.getUserNick(id) }
            .subscribe({  nick: String ->
                repo.saveUserLocally(nick,email)
                view.hideLoading()
                view.openMainActivity()
            },{t: Throwable? ->
                view.hideLoading()
                view.showMessage("Nie udało się!")
            }))
    }

}