package com.konradpekala.blefik.ui.room

import com.konradpekala.blefik.ui.base.BasePresenter

class RoomsPresenter<V: RoomsMvp.View>(view: V): BasePresenter<V>(view),
    RoomsMvp.Presenter<V>{


    override fun onAddRoomClick() {
        view.showCreateRoomView()
    }
    override fun onAddRoomClick(name: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}