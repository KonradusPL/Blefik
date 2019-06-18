package com.konradpekala.blefik.domain.error_models

class PlayerIsInRoom: BaseError() {
    override fun getUIMessage(): String {
        return "Już jesteś w tym pokoju!"
    }
}