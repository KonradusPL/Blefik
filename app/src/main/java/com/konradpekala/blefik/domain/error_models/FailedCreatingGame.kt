package com.konradpekala.blefik.domain.error_models

class FailedCreatingGame: BaseError() {
    override fun getUIMessage(): String {
        return "Nie udało się stworzyć gry :("
    }
}