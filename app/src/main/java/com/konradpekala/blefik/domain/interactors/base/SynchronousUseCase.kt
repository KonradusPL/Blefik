package com.konradpekala.blefik.domain.interactors.base

interface SynchronousUseCase<out Result, in Parameter> {

    fun execute(request: Parameter? = null): Result
}