package com.konradpekala.blefik.ui.main

import com.konradpekala.blefik.data.repo.auth.IAuthRepository
import com.konradpekala.blefik.data.repo.image.ImageRepository
import com.konradpekala.blefik.data.repo.profile.ProfileRepository
import com.konradpekala.blefik.ui.base.BasePresenter
import java.io.File

class MainPresenter<V: MainMvp.View>(
    view: V,
    val profileRepo: ProfileRepository,
    val imageRepo: ImageRepository,
    val authRepo: IAuthRepository
): BasePresenter<V>(view),MainMvp.Presenter<V> {

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStart() {
        super.onStart()

        cd.add(imageRepo.getProfileImage(authRepo.getId()).subscribe({file: File ->
            view.changeProfileImage(file)
        },{t: Throwable? ->
            view.showMessage(t.toString())
        }))

        view.setToolbarTitle(profileRepo.getNick())

    }

}