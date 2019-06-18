package com.konradpekala.blefik.ui.createProfile

import com.konradpekala.blefik.data.model.user.User
import com.konradpekala.blefik.data.repository.CreateProfileRepo
import com.konradpekala.blefik.ui.base.BasePresenter

class CreateProfilePresenter<V: CreateProfileMvp.View>(view: V, val repo: CreateProfileRepo)
    : BasePresenter<V>(view),
    CreateProfileMvp.Presenter<V> {


    override fun onAddUserClick(nick: String) {
        if(nick.isEmpty()){
            view.showMessage("Uzupepłnij pole !")
            return
        }

        cd.add(repo.addUser(User(nick, "", "", "")).subscribe {
            view.showMessage("Udało się !")
            view.openRoomActivity()
        })
    }


}