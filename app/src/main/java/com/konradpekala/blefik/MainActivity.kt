package com.konradpekala.blefik

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.ui.base.BaseActivity
import com.konradpekala.blefik.ui.game.GameActivity
import com.konradpekala.blefik.ui.main.MainMvp
import com.konradpekala.blefik.ui.main.adapters.MainFragmentsAdapter
import com.konradpekala.blefik.ui.main.rooms.RoomsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(),MainMvp.View {

    private lateinit var mRoomsFragment: RoomsFragment
    private lateinit var mRankingFragment: Fragment

    private lateinit var mFragmentAdapter: MainFragmentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRoomsFragment = RoomsFragment()
        mRankingFragment = Fragment()

        mFragmentAdapter = MainFragmentsAdapter(supportFragmentManager,this)
        viewPager.adapter = mFragmentAdapter
        tabLayoutMain.setupWithViewPager(viewPager)
    }

    override fun openGameActivity(room: Room) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("roomId",room.roomId)
        intent.putExtra("roomName",room.name)
        intent.putExtra("creatorId",room.name)
        startActivity(intent)
        finish()
    }

    fun getRoomsFragment() = mRoomsFragment
    fun getRankingFragment() = mRankingFragment
}
