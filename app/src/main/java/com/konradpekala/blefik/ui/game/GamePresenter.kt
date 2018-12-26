package com.konradpekala.blefik.ui.game

import android.util.Log
import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.data.model.UpdateType
import com.konradpekala.blefik.data.repo.GameRepo
import com.konradpekala.blefik.ui.base.BasePresenter

class GamePresenter<V: GameMvp.View>(view: V,val repo: GameRepo): BasePresenter<V>(view),
    GameMvp.Presenter<V> {

    private var mRoom: Room? = null

    override fun startGame(roomId: String, creatorId: String) {
        Log.d("startGame","true")
        var firstTime = true

        cd.add(repo.observeRoom(roomId)
            .subscribe({room: Room ->
                Log.d("onNext vs doOnNext","onNext")
                view.showMessage("Dostałem rooma!")

                mRoom = room
                if(firstTime){
                    firstTime = false
                    view.getPlayersAdapter().refresh(room.players)
                    if(repo.playerIsCreator())
                        newRound()
                }
                if(room.updateType == UpdateType.NewGame){
                    view.getPlayersAdapter().refresh(room.players)
                    view.getPlayerCardsAdapter().refreshCards(repo.getPlayer()!!.currentCards)
                }
            },{t: Throwable? ->
                view.showMessage(t.toString())
            }))
    }

    private fun newRound(){
        val room = Room(mRoom!!)
        Log.d("whereIsArray",room.players.toString())
        cd.add(repo.makeNewRound(room)
            .subscribe({
                view.showMessage("Pokój updated!")
            },{t: Throwable? ->
                Log.d("newRound",t.toString())
                view.showMessage(t.toString())
            }))
    }

}