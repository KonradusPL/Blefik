package com.konradpekala.blefik.ui.room

import android.util.Log
import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.data.repo.RoomsRepo
import com.konradpekala.blefik.ui.base.BasePresenter

class RoomsPresenter<V: RoomsMvp.View>(view: V,val repo: RoomsRepo): BasePresenter<V>(view),
    RoomsMvp.Presenter<V>{

    private var gameOpened = false

    override fun start() {
        super.start()
        gameOpened = false
        cd.add(repo.observeRooms()
            .subscribe({room: Room ->
                if(room.isChoosenByPlayer && room.started && !gameOpened) {
                    gameOpened = true
                    view.openGameActivity(room)
                }
                view.getListAdapter().updateRooms(room)

            },{t: Throwable? ->
                view.showMessage(t.toString())
            }))
    }

    override fun stop() {
        Log.d("stopRooms","true")
        super.stop()
        repo.database.clean()
    }

    override fun onAddRoomClick() {
        view.showCreateRoomView()
    }
    override fun onAddRoomClick(name: String) {
        cd.add(repo.addRoom(name)
            .subscribe({t: String? ->
                view.showMessage("Udało się!")
            },{t: Throwable? ->
                view.showMessage(":(")
            }))
    }

    override fun onRoomClick(room: Room) {

        if(repo.isSameAsChosenBefore(room))
            return

        if (repo.playerIsInRoom(room)){
            Log.d("onRoomClick","hasPlayer")
            room.isChoosenByPlayer = true
            view.getListAdapter().showRoomLoading(room)
            return
        }
        cd.add(repo.addUserToRoom(room)
            .subscribe({
                Log.d("onRoomClick","success")
            },{t: Throwable? ->
                Log.d("onRoomClick",t.toString())
            }))
    }

    override fun onStartGameClick(room: Room) {
        if(room.players.size < 2){
            view.showMessage("Do gry potrzeba conajmniej 2 graczy")
            return
        }

        cd.add(repo.changeRoomToStarted(room)
            .subscribe({
            },{t: Throwable? ->
                view.showMessage("Nie udało się stworzyć gry :(")
            }))
    }

}