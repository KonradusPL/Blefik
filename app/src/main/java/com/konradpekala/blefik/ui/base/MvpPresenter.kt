package com.konradpekala.blefik.ui.base

interface MvpPresenter<V: MvpView> {
    fun onCreate()
    fun start()
    fun stop()
}