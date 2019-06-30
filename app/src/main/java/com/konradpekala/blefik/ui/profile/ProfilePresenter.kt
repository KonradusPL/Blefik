package com.konradpekala.blefik.ui.profile

import android.util.Log
import com.konradpekala.blefik.data.auth.UserSession
import com.konradpekala.blefik.data.model.user.User
import com.konradpekala.blefik.data.repository.image.ImageRepository
import com.konradpekala.blefik.data.repository.image.UrlType
import com.konradpekala.blefik.data.repository.users.UserRepository
import com.konradpekala.blefik.domain.UserUpdateRequest
import com.konradpekala.blefik.domain.interactors.auth.LogOutUseCase
import com.konradpekala.blefik.domain.interactors.user.GetLocalUserUseCase
import com.konradpekala.blefik.domain.interactors.user.UpdateImageUseCase
import com.konradpekala.blefik.domain.interactors.user.UpdateUserUseCase
import com.konradpekala.blefik.ui.base.NewBasePresenter
import com.konradpekala.blefik.utils.schedulers.ValueToUpdate
import javax.inject.Inject

class ProfilePresenter<V: ProfileMvp.View> @Inject constructor(
    private val mGetLocalUserUseCase: GetLocalUserUseCase,
    private val mUpdateImageUseCase: UpdateImageUseCase,
    private val mUpdateUserUseCase: UpdateUserUseCase,
    private val mLogOutUseCase: LogOutUseCase):
    NewBasePresenter<V>(),ProfileMvp.Presenter<V> {

    private val TAG = "ProfilePresenter"

    override fun onCreate() {
        super.onCreate()

        mGetLocalUserUseCase.excecute(
            onSuccess = { user: User ->
                Log.d(TAG,"mGetLocalUserUseCase: success")
                view.changeNick(user.nick)
                view.changeEmail(user.email)
                view.changeProfileImage(user.image.url)
            },onError = {error: Throwable ->
                Log.d(TAG,"mGetLocalUserUseCase: ${error.message}")
            }
        )
    }

    override fun onChangeNickClick(newNick: String) {
        val request = UserUpdateRequest(newNick,ValueToUpdate.NICK)

        mUpdateUserUseCase.excecute(request,{
            view.changeNick(newNick)
            view.showMessage("Powodzenia $newNick!")
        },{t: Throwable ->
            Log.e(TAG,t.toString())
            view.showMessage("Nie udało się zmienić na $newNick")
        })
    }

    override fun onChangeEmailClick(newEmail: String) {
        val request = UserUpdateRequest(newEmail,ValueToUpdate.EMAIL)

        mUpdateUserUseCase.excecute(request,{
            view.changeEmail(newEmail)
            view.showMessage("Twój email to teraz $newEmail")
        },{t: Throwable ->
            Log.e(TAG,t.toString())
            view.showMessage("Nie udało się zmienić emailu na $newEmail")
        })
    }

    override fun onLogOutClick() {
        mLogOutUseCase.execute()
        view.openLoginActivity()
    }

    override fun onNewImageChosen(imagePath: String) {
        Log.d(TAG, imagePath)

        mUpdateImageUseCase.excecute(imagePath,{ remoteImageUrl: String ->
            view.changeProfileImage(remoteImageUrl)
            Log.d("onNewImageChosen",remoteImageUrl)
            Log.d("onNewImageChosen","success")
        },{t: Throwable ->
            Log.d("onNewImageChosen",t.toString())
        })

    }
}