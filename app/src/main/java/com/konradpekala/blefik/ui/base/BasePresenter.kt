package com.konradpekala.blefik.ui.base

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<V: MvpView>(var view: V): MvpPresenter<V> {

    protected val cd = CompositeDisposable()

    override fun onCreate() {

    }

    override fun onAttach(view: V) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStart() {
    }

    override fun onStop() {
        cd.clear()
    }
}