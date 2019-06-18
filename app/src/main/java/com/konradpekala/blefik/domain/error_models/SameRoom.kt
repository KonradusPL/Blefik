package com.konradpekala.blefik.domain.error_models

class SameRoom: BaseError() {
    override fun getUIMessage(): String {
        return "Już wybrałeś ten pokój!"
    }
}