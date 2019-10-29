package com.konradpekala.blefik.ui.game

import android.util.Log
import com.konradpekala.blefik.data.model.Bid
import com.konradpekala.blefik.data.model.Player
import com.konradpekala.blefik.data.model.room.Room
import com.konradpekala.blefik.data.model.room.UpdateType
import com.konradpekala.blefik.data.model.user.User
import com.konradpekala.blefik.data.repository.GameRepo
import com.konradpekala.blefik.domain.error_models.BaseError
import com.konradpekala.blefik.domain.interactors.game.*
import com.konradpekala.blefik.domain.interactors.user.GetLocalUserUseCase
import com.konradpekala.blefik.domain.model.CheckBidResponse
import com.konradpekala.blefik.domain.model.CheckTurnResponse
import com.konradpekala.blefik.ui.base.NewBasePresenter
import com.konradpekala.blefik.ui.main.rooms.QWERTY
import com.konradpekala.blefik.utils.CardsUtils
import org.jetbrains.anko.getStackTraceString
import javax.inject.Inject


//Game -> Round -> Turn
class GamePresenter<V: GameMvp.View> @Inject constructor(
    private val mMakeNewRoundUseCase: MakeNewRoundUseCase,
    private val mUpdateBidUseCase: UpdateBidUseCase,
    private val mMakeNewTurnUseCase: MakeNewTurnUseCase,
    private val mGetLocalUserUseCase: GetLocalUserUseCase,
    private val mCheckTurnUseCase: CheckTurnUseCase,
    private val mObserveGameChangesUseCase: ObserveGameChangesUseCase,
    private val mSetGameToStartedUseCase: StartGameUseCase,
    private val mCleanGameSessionUseCase: CleanGameSessionUseCase):
    NewBasePresenter<V>() {

    private val TAG = "GamePresenter"

    var mNeedToRefresh = false

    private lateinit var mLocalPlayer: Player

    fun startGame(firstT: Boolean) {
        Log.d("startGame","true")

        mLocalPlayer = Player()

        mSetGameToStartedUseCase.execute()

        mGetLocalUserUseCase.excecute(onSuccess = {user: User ->
            mLocalPlayer.fromUser(user)
            startObservingGame(firstT)
        },onError = {t: Throwable ->
            Log.d(TAG,"mGetLocalUserUseCase: ${t.getStackTraceString()}")
        })
    }

    private fun startObservingGame(firstT: Boolean) {
        var firstTime = firstT

        mObserveGameChangesUseCase.execute(null,{ room: Room ->
            Log.d(QWERTY,"mObserveGameChangesUseCase")
            Log.d(TAG,"next room update: $room")
            if(firstTime){
                firstTime = false
                view.getPlayersAdapter().refresh(room.players)
                if(room.locallyCreated)
                    newRound(true)
            }
            when(room.updateType){
                UpdateType.NewGame -> {
                    view.getPlayersAdapter().refresh(room.players)
                    room.updateCardsForGivenPlayer(mLocalPlayer)
                    view.getPlayerCardsAdapter().refreshCards(mLocalPlayer.currentCards)
                    view.animateBidChanges()
                    view.getBidAdapter().refreshCards(emptyList())
                }
                UpdateType.NewBid -> {
                    view.animateBidChanges()
                    view.getBidAdapter().refreshCards(CardsUtils.generateCardsForBid(room.currentBid))
                }
                UpdateType.NextPlayer -> {
                    view.getPlayersAdapter().refresh(room.players)
                }
                UpdateType.GameEnded -> onGameEnd()
                else -> {}
            }
        },{t: Throwable ->
            Log.d(TAG,"mObserveGameChangesUseCase: ${t.getStackTraceString()}")
        })
    }

    fun refreshGame() {
        cd.clear()
        mNeedToRefresh = true
        startGame(false)
    }

    private fun newRound(isRoundFirst: Boolean){
        mMakeNewRoundUseCase.excecute(isRoundFirst,{
            view.showMessage("Nowa runda!")
        },{t: Throwable ->
            Log.d(TAG,"mMakeNewRoundUseCase: ${t.getStackTraceString()}")
        })
    }

    fun onRaiseBidClick() {
        view.openBidCreator()
    }

    fun onCreateBidClick(bid: Bid) {
        view.closeBidCreator()

        mUpdateBidUseCase.excecute(bid,
            onComplete = {
                addNewTurn()
            },
            onError =  {error: Throwable ->
                if(error is BaseError) view.showMessage(error.message!!)
                Log.d(TAG,"mUpdateBidUseCase: $error")
        })
    }

    private fun addNewTurn(){
        mMakeNewTurnUseCase.excecute(
            onComplete = {
                view.showMessage("Nowy zawodnik!")
            },
            onError = {t: Throwable ->
                Log.d(TAG,"addNewTurn: $t")
            })
    }

    fun onCheckBidClick() {
        mCheckTurnUseCase.excecute(
            onSuccess = {response: CheckTurnResponse ->
                onCheckBidResponse(response.checkBidResponse)
                if (response.isSomeoneRemoved)
                    onPlayerRemoved(response.playerRemoved!!)
                if (response.doesGameNeedToEnd){
                    //onGameEnd() < this method is not used since every
                    // player will receive updated, ended room and then app will end the game
                }
                else
                    newRound(false)
            },onError = {t: Throwable ->
                Log.d(TAG,"mCheckTurnUseCase: ${t.getStackTraceString()}")
            })
    }

    private fun onGameEnd() {
        mCleanGameSessionUseCase.execute()
        onDispose()

        view.openMainActivity()
    }

    private fun onDispose(){
        mMakeNewRoundUseCase.dispose()
        mUpdateBidUseCase.dispose()
        mMakeNewTurnUseCase.dispose()
        mGetLocalUserUseCase.dispose()
        mCheckTurnUseCase.dispose()
        mObserveGameChangesUseCase.dispose()
    }

    private fun onPlayerRemoved(playerRemoved: Player) {
        view.showMessage("${playerRemoved.nick} przegrał!")
    }

    private fun onCheckBidResponse(response: CheckBidResponse) {
        when(response){
            CheckBidResponse.BID_NOT_IN_CARDS -> view.showMessage("Wygrana runda!")
            CheckBidResponse.BID_IN_CARDS -> view.showMessage("Przegrana runda")
        }
    }
}