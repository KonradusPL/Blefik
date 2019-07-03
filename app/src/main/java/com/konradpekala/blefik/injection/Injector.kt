package com.konradpekala.blefik.injection

import android.content.Context
import com.konradpekala.blefik.data.auth.FirebaseUserSession
import com.konradpekala.blefik.data.database.FirebaseDatabase
import com.konradpekala.blefik.data.repository.*
import com.konradpekala.blefik.ui.game.GameMvp
import com.konradpekala.blefik.ui.game.GamePresenter
import com.konradpekala.blefik.ui.main.rooms.RoomsMvp
import com.konradpekala.blefik.ui.main.rooms.RoomsPresenter
import com.konradpekala.blefik.utils.CardsUtils
import com.konradpekala.blefik.utils.PhoneStuff

object Injector {
    private var mRoomPresenter: RoomsPresenter<RoomsMvp.View>? = null
    private var mGamePresenter: GamePresenter<GameMvp.View>? = null

    private val mAuth = com.google.firebase.auth.FirebaseAuth.getInstance()
    /*fun getProfilePresenter(view: ProfileMvp.View, ctx: Context): ProfilePresenter<ProfileMvp.View>{
        return ProfilePresenter(view,UserRepository(
            FirebaseUserRepository(UserSession(mAuth), FirebaseStorage()),
            SharedPrefs(ctx), UserSession(mAuth)
        ),ImageRepository(FirebaseImageStorage(),
            LocalImageRepository(ctx)
        ),
            AuthFirebaseRepository(UserSession(mAuth)))
    }*/
}