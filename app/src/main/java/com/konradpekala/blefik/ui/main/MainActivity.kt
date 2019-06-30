package com.konradpekala.blefik.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.konradpekala.blefik.R
import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.ui.base.BaseActivity
import com.konradpekala.blefik.ui.game.GameActivity
import com.konradpekala.blefik.ui.login.LoginActivity
import com.konradpekala.blefik.ui.main.adapters.MainFragmentsAdapter
import com.konradpekala.blefik.ui.main.ranking.RankingFragment
import com.konradpekala.blefik.ui.main.rooms.RoomsFragment
import com.konradpekala.blefik.ui.profile.ProfileActivity
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import javax.inject.Inject

class MainActivity : BaseActivity(),MainMvp.View {

    private val TAG = "MainActivity"

    private lateinit var mRoomsFragment: RoomsFragment
    private lateinit var mRankingFragment: RankingFragment
    private lateinit var mFragmentAdapter: MainFragmentsAdapter

    @Inject
    lateinit var mPresenter: MainPresenter<MainMvp.View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        setContentView(R.layout.activity_main)

        mRoomsFragment = RoomsFragment()
        mRankingFragment = RankingFragment()

        initButtons()
        setSupportActionBar(toolbarMain)
        initTabsStuff()

        mPresenter.onAttach(this)
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

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy")
    }

    override fun onStart() {
        super.onStart()
        mPresenter.onStart()
    }

    override fun openProfileActivity() {
        val code = resources.getInteger(R.integer.main_to_profile_code)
        val intent = Intent(this, ProfileActivity::class.java)
        startActivityForResult(intent,code)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val mainToProfileCode = resources.getInteger(R.integer.main_to_profile_code)

        if(requestCode == mainToProfileCode && resultCode == Activity.RESULT_OK)
            finish()
    }

    override fun openLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun getRoomsFragment() = mRoomsFragment
    fun getRankingFragment() = mRankingFragment
}
