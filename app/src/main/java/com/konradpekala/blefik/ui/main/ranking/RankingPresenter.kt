package com.konradpekala.blefik.ui.main.ranking

import com.konradpekala.blefik.data.model.User
import com.konradpekala.blefik.data.repository.RankingRepository
import com.konradpekala.blefik.ui.base.BasePresenter

class RankingPresenter<V: RankingMvp.View>(view: V,val rankingRepository: RankingRepository)
    :BasePresenter<V>(view), RankingMvp.Presenter<V> {

    override fun onLoadData() {
        cd.add(rankingRepository.getUsers().subscribe( {users: List<User>  ->
            view.showRankingList(users)
        },{t: Throwable ->
            view.showMessage(t.toString())
        }))
    }


}