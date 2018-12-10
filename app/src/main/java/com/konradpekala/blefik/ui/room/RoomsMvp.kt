package com.konradpekala.blefik.ui.room

import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.ui.base.MvpPresenter
import com.konradpekala.blefik.ui.base.MvpView

interface RoomsMvp {
    interface View: MvpView{
        fun showRooms(list: List<Room>)
        fun updateRooms(room: Room)
        fun showRoomLoading(room: Room)
        fun hideRoomLoading()
        fun openGameActivity()
        fun showCreateRoomView()
        fun getPresenter(): Presenter<View>
    }
    interface Presenter<V: View>: MvpPresenter<V>{
        fun onAddRoomClick()
        fun onAddRoomClick(name: String)
        fun onRoomClick(room: Room)
    }
}