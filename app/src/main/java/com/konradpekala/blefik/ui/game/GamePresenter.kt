package com.konradpekala.blefik.ui.game

import android.util.Log
import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.data.repo.GameRepo
import com.konradpekala.blefik.ui.base.BasePresenter

class GamePresenter<V: GameMvp.View>(view: V,val repo: GameRepo): BasePresenter<V>(view),
    GameMvp.Presenter<V> {

    private var mRoom: Room? = null

    override fun startGame(roomId: String, creatorId: String) {
        var firstTime = true

        cd.add(repo.observeRoom(roomId)
            .subscribe({room: Room ->
                view.showMessage("Dostałem rooma!")

                mRoom = room
                if(firstTime){
                    firstTime = false
                    view.getPlayersAdapter().refreshCards(room.players)
                    if(room.creatorId == repo.phoneStuff.getAndroidId()){
                        newRound()
                    }
                }
            },{t: Throwable? ->
                view.showMessage(t.toString())
            }))
    }

    private fun newRound(){
        cd.add(repo.makeNewRound(mRoom!!)
            .subscribe({
                view.showMessage("Pokój updated!")
            },{t: Throwable? ->
                Log.d("newRound",t.toString())
                view.showMessage(t.toString())
            }))
    }

}