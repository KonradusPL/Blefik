package com.konradpekala.blefik.ui.base

import io.reactivex.disposables.CompositeDisposable

open class BasePresenter<V: MvpView>(var view: V): MvpPresenter<V> {

    protected val cd = CompositeDisposable()

    override fun onCreate() {

    }

    override fun start() {
    }

    override fun stop() {
        cd.clear()
    }
}