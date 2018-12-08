package com.konradpekala.blefik.injection

import android.content.Context
import com.konradpekala.blefik.data.database.FirebaseDatabase
import com.konradpekala.blefik.data.preferences.SharedPreferences
import com.konradpekala.blefik.data.repo.RoomsRepo
import com.konradpekala.blefik.ui.room.RoomsMvp
import com.konradpekala.blefik.ui.room.RoomsPresenter
import com.konradpekala.blefik.utils.PhoneStuff

object Injector {
    var mRoomPresenter: RoomsPresenter<RoomsMvp.View>? = null

    fun getRoomPresenter(view: RoomsMvp.View,ctx: Context): RoomsPresenter<RoomsMvp.View>{
        if(mRoomPresenter == null){
            mRoomPresenter = RoomsPresenter(view, RoomsRepo(FirebaseDatabase(), SharedPreferences(ctx), PhoneStuff(ctx)))
        }else
            mRoomPresenter!!.view = view
        return mRoomPresenter!!
    }
}