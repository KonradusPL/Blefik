package com.konradpekala.blefik.ui.main

import com.konradpekala.blefik.data.repo.profile.ProfileRepository
import com.konradpekala.blefik.ui.base.BasePresenter
import java.io.File

class MainPresenter<V: MainMvp.View>(
    view: V,
    val profileRepo: ProfileRepository
): BasePresenter<V>(view),MainMvp.Presenter<V> {

    override fun onCreate() {
        super.onCreate()

        val nick = profileRepo.getNick()
        view.setToolbarTitle(nick)

    }

    override fun onStart() {
        super.onStart()
        cd.add(profileRepo.getProfileImage().subscribe({file: File ->
            view.changeProfileImage(file)
        },{t: Throwable? ->
            view.showMessage(t.toString())
        }))

    }

}