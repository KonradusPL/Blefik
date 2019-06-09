package com.konradpekala.blefik.ui.main.ranking

import com.konradpekala.blefik.data.model.User
import com.konradpekala.blefik.ui.base.MvpPresenter
import com.konradpekala.blefik.ui.base.MvpView
import com.konradpekala.blefik.ui.main.adapters.RankingAdapter

interface RankingMvp {
    interface View: MvpView{
        fun showRankingList(users: List<User>)
        fun getListAdapter(): RankingAdapter
        fun getPresenter(): RankingMvp.Presenter<View>
        fun showLoading()
        fun hideLoading()
    }
    interface Presenter<V: View>: MvpPresenter<V>{
    }
}