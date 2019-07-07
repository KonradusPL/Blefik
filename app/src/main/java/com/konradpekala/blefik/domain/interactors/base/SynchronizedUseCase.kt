package com.konradpekala.blefik.domain.interactors.base

interface SynchronizedUseCase<out Result, in Parameter> {

    fun execute(request: Parameter? = null): Result
}