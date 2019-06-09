package com.konradpekala.blefik.ui.base

interface MvpPresenter<V: MvpView> {
    fun onCreate()
    fun onStart()
    fun onStop()
    fun onAttach(view: V)
}