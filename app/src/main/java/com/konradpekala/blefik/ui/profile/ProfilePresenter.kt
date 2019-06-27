package com.konradpekala.blefik.ui.profile

import android.util.Log
import com.konradpekala.blefik.data.auth.UserSession
import com.konradpekala.blefik.data.model.user.User
import com.konradpekala.blefik.data.repository.image.ImageRepository
import com.konradpekala.blefik.data.repository.image.UrlType
import com.konradpekala.blefik.data.repository.users.UserRepository
import com.konradpekala.blefik.domain.interactors.user.GetLocalUserUseCase
import com.konradpekala.blefik.domain.interactors.user.UpdateImageUseCase
import com.konradpekala.blefik.ui.base.NewBasePresenter
import javax.inject.Inject

class ProfilePresenter<V: ProfileMvp.View> @Inject constructor(
    private val userRepo: UserRepository,
    private val imageRepo: ImageRepository,
    private val mUserSession: UserSession,
    private val mGetLocalUserUseCase: GetLocalUserUseCase,
    private val mUpdateImageUseCase: UpdateImageUseCase
):
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
        mUserSession.logOut()
        view.openLoginActivity()
    }

    override fun onNewImageChosen(imagePath: String) {
        Log.d(TAG, imagePath)

        mUpdateImageUseCase.excecute(imagePath,{
            view.changeProfileImage(imageRepo.getImageUrl(UrlType.REMOTE))
            Log.d("onNewImageChosen",imageRepo.getImageUrl(UrlType.REMOTE))
            Log.d("onNewImageChosen","success")
        },{t: Throwable ->
            Log.d("onNewImageChosen",t.toString())
        })

    }
}