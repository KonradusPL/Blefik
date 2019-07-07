package com.konradpekala.blefik.domain.interactors.game.errors

import com.konradpekala.blefik.domain.error_models.BaseError

class BidNotProperlyCreated: BaseError() {
    override fun getUIMessage(): String {
        return "Wybierz poprawnie stawke!"
    }
}