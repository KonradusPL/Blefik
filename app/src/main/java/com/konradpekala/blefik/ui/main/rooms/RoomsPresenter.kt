package com.konradpekala.blefik.ui.main.rooms

import android.util.Log
import com.konradpekala.blefik.data.model.room.Room
import com.konradpekala.blefik.domain.error_models.BaseError
import com.konradpekala.blefik.domain.interactors.room.AddRoomUseCase
import com.konradpekala.blefik.domain.interactors.room.ChangeRoomToStartUseCase
import com.konradpekala.blefik.domain.interactors.room.ObserveRoomsUseCase
import com.konradpekala.blefik.domain.interactors.room.AddUserToRoomUseCase
import com.konradpekala.blefik.ui.base.NewBasePresenter
import org.jetbrains.anko.getStackTraceString
import javax.inject.Inject

class RoomsPresenter<V: RoomsMvp.View> @Inject constructor(
    private val mObserveRoomsUseCase: ObserveRoomsUseCase,
    private val mAddRoomUseCase: AddRoomUseCase,
    private val mAddUserToRoomUseCase: AddUserToRoomUseCase,
    private val mChangeRoomToStartUseCase: ChangeRoomToStartUseCase
)
    : NewBasePresenter<V>(), RoomsMvp.Presenter<V> {

    private val TAG = "RoomsPresenter"

    private var gameOpened = false

    override fun onStart() {
        super.onStart()
        gameOpened = false

        mObserveRoomsUseCase.execute(
            onNext = {room: Room ->
                Log.d(TAG,"mObserveRoomsUseCase:onNext")
                if(room.isChoosenByPlayer && room.hasGameStarted && !gameOpened) {
                    gameOpened = true
                    view.openGameActivity(room)
                }
                view.getListAdapter().updateRooms(room)
            },onError = {error: Throwable ->
                Log.d(TAG,"mObserveRoomsUseCase ${error}")
                Log.d(TAG,"mObserveRoomsUseCase ${error.getStackTraceString()}")
            })
    }

    override fun onStop() {
        Log.d("stopRooms","true")
        super.onStop()
        mObserveRoomsUseCase.dispose()
        mAddUserToRoomUseCase.dispose()
        mAddRoomUseCase.dispose()
        //mRepository.database.cleanCache()
    }

    override fun onAddRoomClick() {
        view.showCreateRoomView()
    }
    override fun onAddRoomClick(name: String) {
        view.getListAdapter().removeRoomsLoading()

        mAddRoomUseCase.excecute(name,
            onComplete = {
                Log.d(TAG,"onAddRoomClick:success")
                view.showMessage("Udało się!")
            },onError = { error: Throwable ->
                Log.d(TAG,"onAddRoomClick:${error.message}")
                view.showMessage(":(")
            })
}

    override fun onRoomClick(room: Room) {

        mAddUserToRoomUseCase.excecute(room,
            onComplete = {
                view.getListAdapter().showRoomLoading(room)
                Log.d(TAG,"mAddUserToRoomUseCase:success")
            },onError = {error: Throwable ->
                Log.d(TAG,"mAddUserToRoomUseCase:${error.message}")
                view.showMessage((error as BaseError).getUIMessage())
            })
    }

    override fun onStartGameClick(room: Room) {
        mChangeRoomToStartUseCase.excecute(room,
            onComplete = {

            },onError = {error: Throwable ->
                view.showMessage((error as BaseError).getUIMessage())
            }
        )
    }
}