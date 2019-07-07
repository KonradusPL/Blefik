package com.konradpekala.blefik.domain.error_models

class EmptyRoom: BaseError() {
    override fun getUIMessage(): String {
        return "Do gry potrzeba conajmniej 2 graczy"
    }
}