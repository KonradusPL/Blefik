package com.konradpekala.blefik.domain

import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseUseCase(protected val threadScheduler: Scheduler,
                           protected val postThreadScheduler: Scheduler) {

    private val disposables = CompositeDisposable()

    open fun dispose(){
        if(!disposables.isDisposed)
            disposables.dispose()
    }

    protected fun addDisposable(disposable: Disposable){
        disposables.add(checkNotNull(disposable) {"disposable cannot be null"})
    }
}