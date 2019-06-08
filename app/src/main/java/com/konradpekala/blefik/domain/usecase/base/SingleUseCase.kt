package com.konradpekala.blefik.domain.usecase.base

import com.konradpekala.blefik.utils.schedulers.OnObserveScheduler
import com.konradpekala.blefik.utils.schedulers.OnSubscribeScheduler
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver

abstract class SingleUseCase<in Parameter, Result>(subscribeScheduler: OnSubscribeScheduler,
                             observeScheduler: OnObserveScheduler)
    : BaseUseCase(subscribeScheduler,observeScheduler) {

    abstract fun buildUseCaseSingle(request: Parameter? = null): Single<Result>

    fun excecute(observer: DisposableSingleObserver<Result>,request: Parameter? = null){
        val single = buildUseCaseSingleWithSchedulers(request)
        val disposable = single.subscribeWith(observer)
        addDisposable(disposable)
    }

    private fun buildUseCaseSingleWithSchedulers(request: Parameter?): Single<Result>{
        return buildUseCaseSingle(request)
            .subscribeOn(subscribeScheduler.scheduler)
            .observeOn(observeScheduler.scheduler)
    }
}