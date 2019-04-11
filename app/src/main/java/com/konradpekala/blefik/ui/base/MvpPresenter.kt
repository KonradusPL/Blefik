package com.konradpekala.blefik.ui.base

interface MvpPresenter<V: MvpView> {
    fun onCreate()
    fun onStart()
    fun stop()
}