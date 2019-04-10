package com.konradpekala.blefik.ui.main

import com.konradpekala.blefik.data.repo.MainRepo
import com.konradpekala.blefik.data.repo.profile.ProfileRepository
import com.konradpekala.blefik.ui.base.BasePresenter
import java.io.File

class MainPresenter<V: MainMvp.View>(
    view: V,
    val repo: MainRepo,
    val profileRepo: ProfileRepository
): BasePresenter<V>(view),MainMvp.Presenter<V> {

    override fun onCreate() {
        super.onCreate()

        val nick = repo.getUserNick()
        view.setToolbarTitle(nick)

        cd.add(profileRepo.getProfileImage().subscribe({file: File ->
            view.changeProfileImage(file)
        },{t: Throwable? ->
            view.showMessage(t.toString())
        }))
    }

    override fun onChangeNickClick(newNick: String) {
        cd.add(repo.changeNick(newNick).subscribe({
            view.setToolbarTitle(newNick)
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