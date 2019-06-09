package com.konradpekala.blefik.ui.profile

import android.util.Log
import com.konradpekala.blefik.data.repository.auth.IAuthRepository
import com.konradpekala.blefik.data.repository.image.ImageRepository
import com.konradpekala.blefik.data.repository.image.UrlType
import com.konradpekala.blefik.data.repository.users.UserRepository
import com.konradpekala.blefik.ui.base.BasePresenter
import java.io.File

class ProfilePresenter<V: ProfileMvp.View>(view: V,
                                           val userRepo: UserRepository,
                                           val imageRepo: ImageRepository,
                                           val authRepo: IAuthRepository):
    BasePresenter<V>(view),ProfileMvp.Presenter<V> {

    private val TAG = "ProfilePresenter"

    override fun onCreate() {
        super.onCreate()

        //view.changeNick(userRepo.getNick())
        view.changeEmail(userRepo.getEmail())

        cd.add(imageRepo.getProfileImage(authRepo.getId()).subscribe({file: File ->
            view.changeProfileImage(file)
        },{t: Throwable? ->
            view.showMessage(t.toString())
        }))
    }

    override fun onChangeNickClick(newNick: String) {
        cd.add(userRepo.setNick(newNick).subscribe({
            view.changeNick(newNick)
            view.showMessage("Powodzenia $newNick!")
        },{t: Throwable? ->
            Log.e(TAG,t.toString())
            view.showMessage("Nie udało się zmienić na $newNick")
        }))
    }

    override fun onLogOutClick() {
        userRepo.clean()
        imageRepo.clean()
        authRepo.logOut()
        view.openLoginActivity()
    }

    override fun onNewImageChosen(path: String) {
        Log.d("onNewImageChosen",path)
        cd.add(imageRepo.saveImage(path,authRepo.getId())
            .andThen{userRepo.saveImageUrl(imageRepo.getImageUrl(UrlType.REMOTE)).subscribe({
                view.changeProfileImage(imageRepo.getImageUrl(UrlType.REMOTE))
                Log.d("onNewImageChosen",imageRepo.getImageUrl(UrlType.REMOTE))
                Log.d("onNewImageChosen","success")
            },{t: Throwable? ->
                Log.d("onNewImageChosen",t.toString())
            })}.subscribe {  })

    }
}