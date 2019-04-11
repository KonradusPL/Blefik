package com.konradpekala.blefik.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.konradpekala.blefik.R
import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.injection.Injector
import com.konradpekala.blefik.ui.base.BaseActivity
import com.konradpekala.blefik.ui.game.GameActivity
import com.konradpekala.blefik.ui.login.LoginActivity
import com.konradpekala.blefik.ui.main.adapters.MainFragmentsAdapter
import com.konradpekala.blefik.ui.main.ranking.RankingFragment
import com.konradpekala.blefik.ui.main.rooms.RoomsFragment
import com.konradpekala.blefik.ui.profile.ProfileActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_change_name.view.*
import java.io.File

class MainActivity : BaseActivity(),MainMvp.View {


    private lateinit var mRoomsFragment: RoomsFragment
    private lateinit var mRankingFragment: RankingFragment
    private lateinit var mFragmentAdapter: MainFragmentsAdapter

    private lateinit var mPresenter: MainPresenter<MainMvp.View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRoomsFragment = RoomsFragment()
        mRankingFragment = RankingFragment()

        mPresenter = Injector.getMainPresenter(this,this)

        initButtons()
        setSupportActionBar(toolbarMain)
        initTabsStuff()

        mPresenter.onCreate()
    }

    private fun initTabsStuff(){
        mFragmentAdapter = MainFragmentsAdapter(supportFragmentManager,this)
        viewPager.adapter = mFragmentAdapter
        tabLayoutMain.setupWithViewPager(viewPager)
    }

    private fun initButtons(){
        imageProfile.setOnClickListener {
            openProfileActivity()
        }
    }

    override fun setToolbarTitle(title: String) {
        toolbarMain.title = "Witaj, $title"
    }

    override fun changeProfileImage(file: File) {
        Picasso.get()
            .load(file)
            .placeholder(R.drawable.user_holder)
            .resize(50,50)
            .centerCrop()
            .into(imageProfile)
    }

    override fun openGameActivity(room: Room) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("roomId",room.roomId)
        intent.putExtra("roomName",room.name)
        intent.putExtra("creatorId",room.name)
        startActivity(intent)
        finish()
    }

    override fun onStart() {
        super.onStart()
        mPresenter.onStart()
    }

    override fun openProfileActivity() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    override fun openLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun getRoomsFragment() = mRoomsFragment
    fun getRankingFragment() = mRankingFragment
}
