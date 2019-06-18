package com.konradpekala.blefik.domain.error_models

abstract class BaseError: Throwable() {
    abstract fun getUIMessage(): String
}