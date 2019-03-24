package com.konradpekala.blefik.ui.main

import com.konradpekala.blefik.data.repo.MainRepo
import com.konradpekala.blefik.ui.base.BasePresenter

class MainPresenter<V: MainMvp.View>(view: V, val repo: MainRepo): BasePresenter<V>(view),MainMvp.Presenter<V> {

    override fun onChangeNickClick(newNick: String) {
        cd.add(repo.changeNick(newNick).subscribe({
            view.showMessage("Powodzenia $newNick!")
        },{t: Throwable? ->
            view.showMessage("Nie udało się zmienić na $newNick")

        }))
    }

    override fun onLogOutClick() {
        repo.logOut()
        view.openLoginActivity()
    }
}