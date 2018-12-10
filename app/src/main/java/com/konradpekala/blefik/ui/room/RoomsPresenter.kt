package com.konradpekala.blefik.ui.room

import com.konradpekala.blefik.data.model.Player
import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.data.repo.RoomsRepo
import com.konradpekala.blefik.ui.base.BasePresenter

class RoomsPresenter<V: RoomsMvp.View>(view: V,val repo: RoomsRepo): BasePresenter<V>(view),
    RoomsMvp.Presenter<V>{

    private var currentRoom: Room? = null

    override fun start() {
        super.start()
        cd.add(repo.observeRooms()
            .subscribe({room: Room? ->
                if(room != null){
                    view.updateRooms(room)
                    if(currentRoom != null && room.name == currentRoom!!.name && room.creatorId == currentRoom!!.creatorId)
                        view.showRoomLoading(room)
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
        currentRoom = Room(name,1,creatorId,false,false)
        currentRoom?.players?.add(player)

        cd.add(repo.addRoom(currentRoom!!)
            .subscribe({t: String? ->
                view.showMessage("Udało się!")
            },{t: Throwable? ->
                view.showMessage(":(")
            }))
    }

    override fun onRoomClick(room: Room) {
        val creator = repo.prefs.getUserName()
        val creatorId = repo.phoneStuff.getAndroidId()
        val player = Player(creatorId,creator)

        currentRoom = room
        cd.add(repo.addUserToRoom(player,currentRoom!!.roomId)
            .subscribe({
                
            },{t: Throwable? ->

            }))
    }

}