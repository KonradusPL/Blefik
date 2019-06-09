package com.konradpekala.blefik.ui.main

import android.util.Log
import com.konradpekala.blefik.data.auth.Auth
import com.konradpekala.blefik.data.repository.auth.IAuthRepository
import com.konradpekala.blefik.data.repository.image.ImageRepository
import com.konradpekala.blefik.data.repository.profile.ProfileRepository
import com.konradpekala.blefik.domain.usecase.GetProfileImageUseCase
import com.konradpekala.blefik.ui.base.BasePresenter
import com.konradpekala.blefik.ui.base.NewBasePresenter
import java.io.File
import javax.inject.Inject

class MainPresenter<V: MainMvp.View> @Inject constructor(
    private val mGetProfileImageUseCase: GetProfileImageUseCase)
    : NewBasePresenter<V>(), MainMvp.Presenter {

    private val TAG = "MainPresenter"

    override fun onCreate() {
        mGetProfileImageUseCase.excecute(
            onSuccess = {file: File ->
                mView.changeProfileImage(file)
            },
            onError = {t: Throwable ->
                Log.e(TAG,t.message)
                mView.showMessage("Nie udało się załadować obraz")
            }
        )

        //view.setToolbarTitle(profileRepo.getNick())

    }

}