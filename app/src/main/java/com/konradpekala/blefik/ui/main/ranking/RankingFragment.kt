package com.konradpekala.blefik.ui.main.ranking


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.konradpekala.blefik.R
import com.konradpekala.blefik.injection.Injector
import com.konradpekala.blefik.ui.base.BaseFragment
import com.konradpekala.blefik.ui.main.MainMvp
import com.konradpekala.blefik.ui.main.adapters.RankingAdapter
import kotlinx.android.synthetic.main.fragment_ranking.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.HORIZONTAL
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import com.konradpekala.blefik.data.model.User
import dagger.android.AndroidInjection
import javax.inject.Inject


class RankingFragment: BaseFragment<MainMvp.View>(),
    RankingMvp.View {

    private lateinit var mRankingAdapter: RankingAdapter

    @Inject
    lateinit var mPresenter: RankingPresenter<RankingMvp.View>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ranking,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initList()
        initUI()

        mPresenter.onAttach(this)
        mPresenter.onCreate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.onStop()
    }

    private fun initUI(){

    }

    override fun showRankingList(users: List<User>) {
        mRankingAdapter.showUsers(users)
    }

    override fun showLoading() {
        loadingBarRanking.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loadingBarRanking.visibility = View.GONE
    }

    private fun initList() {
        mRankingAdapter = RankingAdapter(ArrayList(), this)

        val divider = DividerItemDecoration(parentContext, VERTICAL)
        listRanking.addItemDecoration(divider)

        listRanking.adapter = mRankingAdapter
        listRanking.layoutManager = LinearLayoutManager(parentContext)

    }

    override fun getListAdapter(): RankingAdapter {
        return mRankingAdapter
    }

    override fun getPresenter(): RankingMvp.Presenter<RankingMvp.View> = mPresenter

}