package com.konradpekala.blefik.injection

import android.content.Context
import com.konradpekala.blefik.data.database.FirebaseDatabase
import com.konradpekala.blefik.data.preferences.SharedPrefs
import com.konradpekala.blefik.data.repo.GameRepo
import com.konradpekala.blefik.data.repo.RoomsRepo
import com.konradpekala.blefik.ui.game.GameMvp
import com.konradpekala.blefik.ui.game.GamePresenter
import com.konradpekala.blefik.ui.main.RoomsMvp
import com.konradpekala.blefik.ui.main.RoomsPresenter
import com.konradpekala.blefik.utils.CardsStuff
import com.konradpekala.blefik.utils.PhoneStuff

object Injector {
    private var mRoomPresenter: RoomsPresenter<RoomsMvp.View>? = null
    private var mGamePresenter: GamePresenter<GameMvp.View>? = null

    fun getRoomPresenter(view: RoomsMvp.View,ctx: Context): RoomsPresenter<RoomsMvp.View>{
        /*if(mRoomPresenter == null){
            mRoomPresenter = RoomsPresenter(view, RoomsRepo(FirebaseDatabase(), SharedPrefs(ctx), PhoneStuff(ctx)))
        }else
            mRoomPresenter!!.view = view
        return mRoomPresenter!!*/
        return RoomsPresenter(view, RoomsRepo(FirebaseDatabase(), SharedPrefs(ctx), PhoneStuff(ctx)))
    }

    fun getGamePresenter(view: GameMvp.View,ctx: Context): GamePresenter<GameMvp.View>{
        /*if(mGamePresenter == null){
            mGamePresenter = GamePresenter(view, GameRepo(FirebaseDatabase(),CardsStuff,SharedPrefs(ctx),PhoneStuff(ctx)))
        }else
            mGamePresenter!!.view = view
        return mGamePresenter!!*/
        return GamePresenter(view, GameRepo(FirebaseDatabase(),CardsStuff,SharedPrefs(ctx),PhoneStuff(ctx)))
    }
}