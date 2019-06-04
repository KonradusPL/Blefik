package com.konradpekala.blefik.ui.profile

import android.util.Log
import com.konradpekala.blefik.data.repo.auth.IAuthRepository
import com.konradpekala.blefik.data.repo.image.IImageRepository
import com.konradpekala.blefik.data.repo.image.ImageRepository
import com.konradpekala.blefik.data.repo.image.UrlType
import com.konradpekala.blefik.data.repo.profile.IProfileRepository
import com.konradpekala.blefik.ui.base.BasePresenter
import java.io.File

class ProfilePresenter<V: ProfileMvp.View>(view: V,
                                           val profileRepo: IProfileRepository,
                                           val imageRepo: ImageRepository,
                                           val authRepo: IAuthRepository):
    BasePresenter<V>(view),ProfileMvp.Presenter<V> {

    override fun onCreate() {
        super.onCreate()

        view.changeNick(profileRepo.getNick())
        view.changeEmail(profileRepo.getEmail())

        cd.add(imageRepo.getProfileImage(authRepo.getId()).subscribe({file: File ->
            view.changeProfileImage(file)
        },{t: Throwable? ->
            view.showMessage(t.toString())
        }))
    }

    override fun onChangeNickClick(newNick: String) {
        cd.add(profileRepo.setNick(newNick).subscribe({
            view.changeNick(newNick)
            view.showMessage("Powodzenia $newNick!")
        },{t: Throwable? ->
            view.showMessage("Nie udało się zmienić na $newNick")
        }))
    }

    override fun onLogOutClick() {
        profileRepo.clean()
        imageRepo.clean()
        authRepo.logOut()
        view.openLoginActivity()
    }

    override fun onNewImageChosen(path: String) {
        Log.d("onNewImageChosen",path)
        cd.add(imageRepo.saveImage(path,authRepo.getId())
            .andThen{profileRepo.saveImageUrl(imageRepo.getImageUrl(UrlType.REMOTE)).subscribe({
                view.changeProfileImage(imageRepo.getImageUrl(UrlType.REMOTE))
                Log.d("onNewImageChosen",imageRepo.getImageUrl(UrlType.REMOTE))
                Log.d("onNewImageChosen","success")
            },{t: Throwable? ->
                Log.d("onNewImageChosen",t.toString())
            })}.subscribe {  })

    }
}