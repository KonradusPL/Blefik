package com.konradpekala.blefik.domain.usecase.base

import com.konradpekala.blefik.utils.schedulers.OnObserveScheduler
import com.konradpekala.blefik.utils.schedulers.OnSubscribeScheduler
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.observers.DisposableCompletableObserver

abstract class CompletableUseCase<in Parameter>(subscribeScheduler: OnSubscribeScheduler,
                                                observeScheduler: OnObserveScheduler
)
    : BaseUseCase(subscribeScheduler,observeScheduler) {

    abstract fun buildUseCaseCompletable(request: Parameter? = null): Completable

    fun excecute(request: Parameter? = null,
                 onComplete: (() -> Unit),
                 onError: ((t: Throwable) -> Unit)){
        val completable = buildUseCaseSingleWithSchedulers(request)
        val disposable = completable.subscribe(onComplete, onError)
        addDisposable(disposable)
    }
    private fun buildUseCaseSingleWithSchedulers(request: Parameter?): Completable {
        return buildUseCaseCompletable(request)
            .subscribeOn(subscribeScheduler.scheduler)
            .observeOn(observeScheduler.scheduler)
    }
}