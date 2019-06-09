package com.konradpekala.blefik.ui.base

import io.reactivex.disposables.CompositeDisposable

abstract class NewBasePresenter<V: MvpView>: MvpPresenter<V> {

    protected val cd = CompositeDisposable()

    protected lateinit var mView: V

    override fun onAttach(view: V) {
        mView = view
    }

    override fun onCreate() {

    }

    override fun onStart() {
    }

    override fun onStop() {
        cd.clear()
    }
}