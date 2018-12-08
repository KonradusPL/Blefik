package com.konradpekala.blefik.ui.room

import com.konradpekala.blefik.data.database.Database
import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.data.repo.RoomsRepo
import com.konradpekala.blefik.ui.base.BasePresenter
import com.konradpekala.blefik.utils.SchedulerProvider
import java.util.*

class RoomsPresenter<V: RoomsMvp.View>(view: V,val repo: RoomsRepo): BasePresenter<V>(view),
    RoomsMvp.Presenter<V>{

    val random = Random().nextInt(100000)

    override fun start() {
        super.start()
        cd.add(repo.observeRooms()
            .subscribe({room: Room? ->
                if(room != null)
                    view.updateRooms(room)
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
        cd.add(repo.addRoom(Room(name,0,creatorId))
            .subscribe({t: String? ->
                view.showMessage("Udało się!")
            },{t: Throwable? ->
                view.showMessage(":(")
            }))
    }

}