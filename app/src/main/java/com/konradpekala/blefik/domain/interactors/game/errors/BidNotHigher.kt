package com.konradpekala.blefik.domain.interactors.game.errors

import com.konradpekala.blefik.domain.error_models.BaseError

class BidNotHigher: BaseError() {
    override fun getUIMessage(): String {
        return "Wybierz wyższą stawke niż poprzednia!"
    }
}