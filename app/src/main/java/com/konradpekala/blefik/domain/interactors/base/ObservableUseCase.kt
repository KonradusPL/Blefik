package com.konradpekala.blefik.domain.interactors.base

import com.konradpekala.blefik.utils.schedulers.OnObserveScheduler
import com.konradpekala.blefik.utils.schedulers.OnSubscribeScheduler
import io.reactivex.Observable
import io.reactivex.Scheduler

abstract class ObservableUseCase< in Params, Result>(subscribeScheduler: Scheduler,
                                                     observeScheduler: Scheduler
) : BaseUseCase(subscribeScheduler, observeScheduler) {

    abstract fun buildUseCaseObservable(params: Params? = null): Observable<Result>

    fun execute(params: Params? = null,
                onNext: ((r: Result) -> Unit),
                onError: ((t: Throwable) -> Unit)) {
        val observable = buildUseCaseObservableWithSchedulers(params)
        val disposable = observable.subscribe(onNext,onError)
        addDisposable(disposable)
    }

    private fun buildUseCaseObservableWithSchedulers(params: Params?): Observable<Result> {
        return buildUseCaseObservable(params)
            .subscribeOn(subscribeScheduler)
            .observeOn(observeScheduler)
    }
}