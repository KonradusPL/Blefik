package com.konradpekala.blefik.ui.profile

import android.util.Log
import com.konradpekala.blefik.data.repo.auth.AuthRepository
import com.konradpekala.blefik.data.repo.profile.ProfileRepository
import com.konradpekala.blefik.ui.base.BasePresenter

class ProfilePresenter<V: ProfileMvp.View>(view: V,
                                           val profileRepo: ProfileRepository,
                                           val authRepo: AuthRepository):
    BasePresenter<V>(view),ProfileMvp.Presenter<V> {

    override fun onCreate() {
        super.onCreate()

        view.changeNick(profileRepo.getNick())
        view.changeEmail(profileRepo.getEmail())
    }

    override fun onChangeNickClick(newNick: String) {
        cd.add(profileRepo.changeNick(newNick).subscribe({
            view.changeNick(newNick)
            view.showMessage("Powodzenia $newNick!")
        },{t: Throwable? ->
            view.showMessage("Nie udało się zmienić na $newNick")
        }))
    }

    override fun onLogOutClick() {
        authRepo.logOut()
        view.openLoginActivity()
    }

    override fun onNewImageChosen(path: String) {
        Log.d("onNewImageChosen",path)
        cd.add(profileRepo.saveImage(path).subscribe({
            view.changeProfileImage(path)
            Log.d("onNewImageChosen","success")
        },{t: Throwable? ->
            Log.d("onNewImageChosen",t.toString())
        }))
    }
}