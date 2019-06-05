package com.konradpekala.blefik.domain.usecase

import com.konradpekala.blefik.domain.BaseUseCase
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import kotlin.math.sin

abstract class SingleUseCase<in Parameter, Result>(tScheduler: Scheduler,
                             pTScheduler: Scheduler): BaseUseCase(tScheduler,pTScheduler) {

    abstract fun buildUseCaseSingle(p: Parameter? = null): Single<Result>

    fun excecute(observer: DisposableSingleObserver<Result>,p: Parameter? = null){
        val single = buildUseCaseSingleWithSchedulers(p)
        val disposable = single.subscribeWith(observer)
        addDisposable(disposable)
    }

    private fun buildUseCaseSingleWithSchedulers(p: Parameter?): Single<Result>{
        return buildUseCaseSingle(p)
            .subscribeOn(threadScheduler)
            .observeOn(postThreadScheduler)
    }
}