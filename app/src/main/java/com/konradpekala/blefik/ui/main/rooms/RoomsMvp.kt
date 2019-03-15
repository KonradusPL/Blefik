package com.konradpekala.blefik.ui.main.rooms

import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.ui.base.MvpPresenter
import com.konradpekala.blefik.ui.base.MvpView
import com.konradpekala.blefik.ui.main.adapters.RoomsAdapter

interface RoomsMvp {
    interface View: MvpView{
        fun getListAdapter(): RoomsAdapter
        fun openGameActivity(room: Room)
        fun showCreateRoomView()
        fun getPresenter(): Presenter<View>
    }
    interface Presenter<V: View>: MvpPresenter<V>{
        fun onAddRoomClick()
        fun onAddRoomClick(name: String)
        fun onRoomClick(room: Room)
        fun onStartGameClick(room: Room)
    }
}