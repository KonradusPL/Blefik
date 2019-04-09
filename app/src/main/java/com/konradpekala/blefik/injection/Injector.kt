package com.konradpekala.blefik.injection

import android.content.Context
import com.konradpekala.blefik.data.auth.FirebaseAuth
import com.konradpekala.blefik.data.database.FirebaseDatabase
import com.konradpekala.blefik.data.preferences.SharedPrefs
import com.konradpekala.blefik.data.repo.*
import com.konradpekala.blefik.data.repo.auth.AuthRepository
import com.konradpekala.blefik.data.repo.auth.AuthRepositoryImpl
import com.konradpekala.blefik.data.repo.profile.LocalProfileRepository
import com.konradpekala.blefik.data.repo.profile.ProfileRepositoryImpl
import com.konradpekala.blefik.data.repo.profile.RemoteProfileRepository
import com.konradpekala.blefik.data.storage.FirebaseStorage
import com.konradpekala.blefik.ui.game.GameMvp
import com.konradpekala.blefik.ui.game.GamePresenter
import com.konradpekala.blefik.ui.login.LoginMvp
import com.konradpekala.blefik.ui.login.LoginPresenter
import com.konradpekala.blefik.ui.main.MainMvp
import com.konradpekala.blefik.ui.main.MainPresenter
import com.konradpekala.blefik.ui.main.ranking.RankingMvp
import com.konradpekala.blefik.ui.main.ranking.RankingPresenter
import com.konradpekala.blefik.ui.main.rooms.RoomsMvp
import com.konradpekala.blefik.ui.main.rooms.RoomsPresenter
import com.konradpekala.blefik.ui.profile.ProfileMvp
import com.konradpekala.blefik.ui.profile.ProfilePresenter
import com.konradpekala.blefik.utils.CardsStuff
import com.konradpekala.blefik.utils.PhoneStuff

object Injector {
    private var mRoomPresenter: RoomsPresenter<RoomsMvp.View>? = null
    private var mGamePresenter: GamePresenter<GameMvp.View>? = null

    fun getRoomPresenter(view: RoomsMvp.View, ctx: Context): RoomsPresenter<RoomsMvp.View> {
        return RoomsPresenter(
            view,
            RoomsRepo(FirebaseDatabase(), SharedPrefs(ctx), FirebaseAuth())
        )
    }

    fun getGamePresenter(view: GameMvp.View,ctx: Context): GamePresenter<GameMvp.View>{

        return GamePresenter(view, GameRepo(FirebaseDatabase(),CardsStuff,FirebaseAuth(),PhoneStuff(ctx)))
    }

    fun getLoginPresenter(view: LoginMvp.View,ctx: Context): LoginPresenter<LoginMvp.View>{
        return LoginPresenter(view, LoginRepository(FirebaseAuth(),FirebaseDatabase(),SharedPrefs(ctx.applicationContext)))
    }
    fun getMainPresenter(view: MainMvp.View,ctx: Context): MainPresenter<MainMvp.View>{
        return MainPresenter(view, MainRepo(FirebaseDatabase(),FirebaseAuth(),SharedPrefs(ctx)))
    }
    fun getRankingPresenter(view: RankingMvp.View, ctx: Context): RankingPresenter<RankingMvp.View>{
        return RankingPresenter(view, RankingRepository(FirebaseDatabase()))
    }
    fun getProfilePresenter(view: ProfileMvp.View, ctx: Context): ProfilePresenter<ProfileMvp.View>{
        return ProfilePresenter(view,ProfileRepositoryImpl(
            RemoteProfileRepository(FirebaseDatabase(), FirebaseAuth(), FirebaseStorage()),
            LocalProfileRepository(), SharedPrefs(ctx)),AuthRepositoryImpl(FirebaseAuth()))
    }
}