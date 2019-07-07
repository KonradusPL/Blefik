package com.konradpekala.blefik.ui.main

import android.util.Log
import com.konradpekala.blefik.data.model.user.User
import com.konradpekala.blefik.domain.interactors.user.GetLocalUserUseCase
import com.konradpekala.blefik.domain.interactors.user.GetProfileImageUseCase
import com.konradpekala.blefik.ui.base.NewBasePresenter
import java.io.File
import javax.inject.Inject

class MainPresenter<V: MainMvp.View> @Inject constructor(
    private val mGetProfileImageUseCase: GetProfileImageUseCase,
    private val mGetLocalUserUseCase: GetLocalUserUseCase)
    : NewBasePresenter<V>(), MainMvp.Presenter {

    private val TAG = "MainPresenter"

    override fun onCreate() {
        mGetProfileImageUseCase.excecute(
            onSuccess = {file: File ->
                view.changeProfileImage(file)
            },
            onError = {t: Throwable ->
                Log.e(TAG,t.message)
                view.showMessage("Nie udało się załadować obraz")
            }
        )
        mGetLocalUserUseCase.excecute(
            onSuccess = {user: User ->
                view.setToolbarTitle(user.nick)
            },
            onError = {error: Throwable ->
                Log.d(TAG,error.toString())
            }
        )
    }

}