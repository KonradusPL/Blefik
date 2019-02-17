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
                Log.d("startGame",room.currentBid?.name ?: "ASd")

                if(firstTime){
                    firstTime = false
                    view.getPlayersAdapter().refresh(room.players)
                    if(repo.playerIsCreator())
                        newRound(true)
                }
                when(room.updateType){
                    UpdateType.NewGame -> {
                        view.getPlayersAdapter().refresh(room.players)
                        view.getPlayerCardsAdapter().refreshCards(repo.getPlayer()!!.currentCards)
                        view.getBidAdapter().refreshCards(emptyList())
                    }
                    UpdateType.NewBid -> {
                        view.getBidAdapter().refreshCards(repo.generateBidCards())
                    }
                    UpdateType.NextPlayer -> {
                        view.getPlayersAdapter().refresh(room.players)
                    }
                    UpdateType.PlayerBeaten -> {
                        if (room.players.size == 1){
                            view.showMessage("${room.players[0].nick} wygrał!")
                            view.openRoomActivity()
                        }
                    }
                    else -> {}
                }
            },{t: Throwable? ->
            }))
    }

    private fun newRound(firstRound: Boolean){
        cd.add(repo.makeNewRound(firstRound)
            .subscribe({
                view.showMessage("Nowa runda!")
            },{t: Throwable? ->
                Log.d("newRound",t.toString())
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

        view.hideKeyboard()

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
            view.showMessage("Przegrana runda!")
            repo.addCardToPlayer(false)
        }else{
            view.showMessage("Wygrana runda!")
            repo.addCardToPlayer(true)
        }

        val beaten = repo.isSomeoneBeaten()
        if(beaten != null){
            cd.add(repo.removePlayer(beaten)
                .subscribe({
                    view.showMessage("${beaten.nick} przegrał!")
                    if(repo.getRoom()!!.players.size == 1)
                        view.openRoomActivity()
                    else
                        newRound(false)
                },{t ->
                    Log.d("removePlayer",t.toString())
                    view.showMessage("Nie udało się usunąć gracza")
                }))
        }else
            newRound(false)
    }

}