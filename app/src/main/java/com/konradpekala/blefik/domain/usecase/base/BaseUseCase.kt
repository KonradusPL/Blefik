package com.konradpekala.blefik.domain.usecase.base

import com.konradpekala.blefik.utils.schedulers.OnObserveScheduler
import com.konradpekala.blefik.utils.schedulers.OnSubscribeScheduler
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseUseCase(protected val subscribeScheduler: OnSubscribeScheduler,
                           protected val observeScheduler: OnObserveScheduler) {

    private val disposables = CompositeDisposable()

    open fun dispose(){
        if(!disposables.isDisposed)
            disposables.dispose()
    }

    protected fun addDisposable(disposable: Disposable){
        disposables.add(checkNotNull(disposable) {"disposable cannot be null"})
    }
}