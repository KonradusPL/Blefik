package com.konradpekala.blefik.injection

import android.content.Context
import com.konradpekala.blefik.data.auth.Auth
import com.konradpekala.blefik.data.auth.FirebaseAuth
import com.konradpekala.blefik.data.database.FirebaseDatabase
import com.konradpekala.blefik.data.preferences.SharedPrefs
import com.konradpekala.blefik.data.repository.*
import com.konradpekala.blefik.data.repository.auth.AuthFirebaseRepository
import com.konradpekala.blefik.data.repository.image.FirebaseImageRepository
import com.konradpekala.blefik.data.repository.image.ImageRepository
import com.konradpekala.blefik.data.repository.users.UserRepository
import com.konradpekala.blefik.data.repository.users.FirebaseUserRepository
import com.konradpekala.blefik.data.repository.image.LocalImageRepository
import com.konradpekala.blefik.data.repository.room.RoomsRepository
import com.konradpekala.blefik.data.storage.FirebaseStorage
import com.konradpekala.blefik.ui.game.GameMvp
import com.konradpekala.blefik.ui.game.GamePresenter
import com.konradpekala.blefik.ui.main.rooms.RoomsMvp
import com.konradpekala.blefik.ui.main.rooms.RoomsPresenter
import com.konradpekala.blefik.ui.profile.ProfileMvp
import com.konradpekala.blefik.ui.profile.ProfilePresenter
import com.konradpekala.blefik.utils.CardsStuff
import com.konradpekala.blefik.utils.PhoneStuff

object Injector {
    private var mRoomPresenter: RoomsPresenter<RoomsMvp.View>? = null
    private var mGamePresenter: GamePresenter<GameMvp.View>? = null

    private val mAuth = com.google.firebase.auth.FirebaseAuth.getInstance()

    /*fun getRoomPresenter(view: RoomsMvp.View, ctx: Context): RoomsPresenter<RoomsMvp.View> {
        return RoomsPresenter(
            view,
            RoomsRepository(
                FirebaseDatabase(),
                SharedPrefs(ctx),
                FirebaseAuth()
            )
        )
    }*/

    fun getGamePresenter(view: GameMvp.View,ctx: Context): GamePresenter<GameMvp.View>{

        return GamePresenter(view, GameRepo(FirebaseDatabase(),CardsStuff,FirebaseAuth(mAuth),PhoneStuff(ctx)))
    }

    /*fun getMainPresenter(view: MainMvp.View,ctx: Context): MainPresenter<MainMvp.View>{
        return MainPresenter(view,
             UserRepository(FirebaseUserRepository(FirebaseAuth(),FirebaseStorage()),SharedPrefs(ctx)),
            ImageRepository(FirebaseImageRepository,
                LocalImageRepository(ctx)
            ),
            AuthFirebaseRepository(FirebaseAuth())
        )
    }*/
    /*fun getRankingPresenter(view: RankingMvp.View, ctx: Context): RankingPresenter<RankingMvp.View>{
        return RankingPresenter(view, RankingRepository(FirebaseDatabase()))
    }*/
    fun getProfilePresenter(view: ProfileMvp.View, ctx: Context): ProfilePresenter<ProfileMvp.View>{
        return ProfilePresenter(view,UserRepository(
            FirebaseUserRepository(FirebaseAuth(mAuth), FirebaseStorage()),
            SharedPrefs(ctx), FirebaseAuth(mAuth)
        ),ImageRepository(FirebaseImageRepository(),
            LocalImageRepository(ctx)
        ),
            AuthFirebaseRepository(FirebaseAuth(mAuth)))
    }
}