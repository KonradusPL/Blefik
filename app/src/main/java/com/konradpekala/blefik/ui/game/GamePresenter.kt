package com.konradpekala.blefik.ui.game

import android.util.Log
import com.konradpekala.blefik.data.model.Bid
import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.data.model.UpdateType
import com.konradpekala.blefik.data.repo.GameRepo
import com.konradpekala.blefik.ui.base.BasePresenter

class GamePresenter<V: GameMvp.View>(view: V,val repo: GameRepo): BasePresenter<V>(view),
    GameMvp.Presenter<V> {

    override fun startGame(roomId: String, creatorId: String) {
        Log.d("startGame","true")
        var firstTime = true

        cd.add(repo.observeRoom(roomId)
            .subscribe({room: Room ->
                Log.d("onNext vs doOnNext","onNext")
                view.showMessage("Dostałem rooma!")

                if(firstTime){
                    firstTime = false
                    view.getPlayersAdapter().refresh(room.players)
                    if(repo.playerIsCreator())
                        newRound(true)
                }
                if(room.updateType == UpdateType.NewGame){
                    view.getPlayersAdapter().refresh(room.players)
                    view.getPlayerCardsAdapter().refreshCards(repo.getPlayer()!!.currentCards)
                }
                if(room.updateType == UpdateType.NewBid){
                    view.getBidAdapter().refreshCards(repo.generateBidCards())
                }
                if(room.updateType == UpdateType.NextPlayer){
                    view.getPlayersAdapter().refresh(room.players)
                }
            },{t: Throwable? ->
                view.showMessage(t.toString())
            }))
    }

    private fun newRound(firstRound: Boolean){
        cd.add(repo.makeNewRound(firstRound)
            .subscribe({
                view.showMessage("Pokój updated!")
            },{t: Throwable? ->
                Log.d("newRound",t.toString())
                view.showMessage(t.toString())
            }))
    }

    override fun onRaiseBidClick() {
        view.openBidCreator()
    }

    override fun onCreateBidClick(bid: Bid) {
        view.closeBidCreator()

        if(!repo.isBidProperlyCreated(bid)){
            view.showMessage("Wybierz poprawnie stawke!")
            return
        }
        if(!repo.isNewBidHigher(bid)){
            view.showMessage("Wybierz wyższą stawke niż poprzednia!")
            return
        }

        cd.add(repo.updateBid(bid)
            .concatWith(repo.makeNextPlayer())
            .subscribe({
                view.showMessage("Nowy zawodnik!")
            },{t: Throwable? ->
                Log.d("onCreateBidClick",t.toString())
            }))
    }

    override fun onCheckBidClick() {
        if (!repo.isBidCreated()){
            view.showMessage("Brakuje stawki!")
            return
        }
        if(repo.isBidInCards()){
            view.showMessage("Wygrana runda!")
            repo.addCardToPlayer(true)
        }else{
            view.showMessage("Przegrana runda!")
            repo.addCardToPlayer(false)
        }

        newRound(false)
    }

}