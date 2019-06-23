package com.konradpekala.blefik.injection

import android.content.Context
import com.konradpekala.blefik.data.auth.FirebaseAuth
import com.konradpekala.blefik.data.database.FirebaseDatabase
import com.konradpekala.blefik.data.repository.*
import com.konradpekala.blefik.ui.game.GameMvp
import com.konradpekala.blefik.ui.game.GamePresenter
import com.konradpekala.blefik.ui.main.rooms.RoomsMvp
import com.konradpekala.blefik.ui.main.rooms.RoomsPresenter
import com.konradpekala.blefik.utils.CardsStuff
import com.konradpekala.blefik.utils.PhoneStuff

object Injector {
    private var mRoomPresenter: RoomsPresenter<RoomsMvp.View>? = null
    private var mGamePresenter: GamePresenter<GameMvp.View>? = null

    private val mAuth = com.google.firebase.auth.FirebaseAuth.getInstance()

    fun getGamePresenter(view: GameMvp.View,ctx: Context): GamePresenter<GameMvp.View>{

        return GamePresenter(view, GameRepo(FirebaseDatabase(),CardsStuff,FirebaseAuth(mAuth),PhoneStuff(ctx)))
    }

    /*fun getProfilePresenter(view: ProfileMvp.View, ctx: Context): ProfilePresenter<ProfileMvp.View>{
        return ProfilePresenter(view,UserRepository(
            FirebaseUserRepository(FirebaseAuth(mAuth), FirebaseStorage()),
            SharedPrefs(ctx), FirebaseAuth(mAuth)
        ),ImageRepository(FirebaseImageStorage(),
            LocalImageRepository(ctx)
        ),
            AuthFirebaseRepository(FirebaseAuth(mAuth)))
    }*/
}