package com.konradpekala.blefik.ui.main.ranking

import android.util.Log
import com.konradpekala.blefik.data.model.user.User
import com.konradpekala.blefik.domain.interactors.user.GetUsersUseCase
import com.konradpekala.blefik.ui.base.NewBasePresenter
import javax.inject.Inject

class RankingPresenter<V: RankingMvp.View> @Inject constructor(
    private val mGetUsersUseCase: GetUsersUseCase
) :NewBasePresenter<V>(), RankingMvp.Presenter<V> {

    private val TAG = "RankingPresenter"

    override fun onCreate() {
        view.showLoading()
        mGetUsersUseCase.excecute(
            onSuccess = {users: List<User> ->
                view.hideLoading()
                view.showRankingList(users)
            },
            onError = {t: Throwable ->
                view.hideLoading()
                Log.d(TAG,t.message)
            }
        )
    }

    override fun onStop() {
        super.onStop()
        mGetUsersUseCase.dispose()
    }

}