package com.konradpekala.blefik.ui.room

import android.util.Log
import com.konradpekala.blefik.data.model.Player
import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.data.model.Status
import com.konradpekala.blefik.data.repo.RoomsRepo
import com.konradpekala.blefik.ui.base.BasePresenter

class RoomsPresenter<V: RoomsMvp.View>(view: V,val repo: RoomsRepo): BasePresenter<V>(view),
    RoomsMvp.Presenter<V>{

    private var mCurrentRoom: Room? = null

    override fun start() {
        super.start()
        cd.add(repo.observeRooms()
            .subscribe({room: Room? ->
                if(room != null){
                    var isCreator = false
                    if(mCurrentRoom != null &&
                        mCurrentRoom!!.name == room.name && mCurrentRoom!!.creatorId == room.creatorId) {
                        if(room.status == Status.Changed)
                            mCurrentRoom = room
                        room.isLoading = true

                        if(room.creatorId == repo.phoneStuff.getAndroidId())
                            room.locallyCreated = true
                    }
                    view.updateRooms(room)
                }
            },{t: Throwable? ->
                view.showMessage(t.toString())
            }))
    }

    override fun stop() {
        super.stop()
        repo.database.clean()
    }

    override fun onAddRoomClick() {
        view.showCreateRoomView()
    }
    override fun onAddRoomClick(name: String) {
        val creator = repo.prefs.getUserName()
        val creatorId = repo.phoneStuff.getAndroidId()
        val player = Player(creatorId,creator)
        mCurrentRoom = Room(name = name, creatorId = creatorId)
        mCurrentRoom?.players?.add(player)

        cd.add(repo.addRoom(mCurrentRoom!!)
            .subscribe({t: String? ->
                view.showMessage("Udało się!")
            },{t: Throwable? ->
                view.showMessage(":(")
            }))
    }

    override fun onRoomClick(room: Room) {
        Log.d("onRoomClick","true")
        val creator = repo.prefs.getUserName()
        val creatorId = repo.phoneStuff.getAndroidId()
        val player = Player(creatorId,creator)

        mCurrentRoom = room
        cd.add(repo.addUserToRoom(player,mCurrentRoom!!.roomId)
            .subscribe({
                Log.d("onRoomClick","success")

            },{t: Throwable? ->
                Log.d("onRoomClick",t.toString())
            }))
    }

}