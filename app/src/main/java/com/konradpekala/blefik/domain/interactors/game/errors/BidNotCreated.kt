package com.konradpekala.blefik.domain.interactors.game.errors

import com.konradpekala.blefik.domain.error_models.BaseError

class BidNotCreated: BaseError() {
    override fun getUIMessage(): String {
        return "Brakuje stawki!"
    }
}