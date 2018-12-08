package com.konradpekala.blefik.ui.game

import com.konradpekala.blefik.data.model.Card
import com.konradpekala.blefik.data.model.CardColor
import com.konradpekala.blefik.data.model.CardNumber
import com.konradpekala.blefik.data.repo.GameRepository
import com.konradpekala.blefik.ui.base.BasePresenter

class GamePresenter<V: GameMvp.View>(view: V,val repo: GameRepository): BasePresenter<V>(view),
    GameMvp.Presenter<V> {

    override fun start() {
        super.start()

        val list = listOf(
            Card(
                CardNumber.Ace,
                CardColor.Club
            ),
            Card(
                CardNumber.Jack,
                CardColor.Diamond
            ),
            Card(
                CardNumber.Ten,
                CardColor.Heart
            )
        )

        view.refreshCards(list)

    }

}