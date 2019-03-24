package com.konradpekala.blefik.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.konradpekala.blefik.R
import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.injection.Injector
import com.konradpekala.blefik.ui.base.BaseActivity
import com.konradpekala.blefik.ui.game.GameActivity
import com.konradpekala.blefik.ui.login.LoginActivity
import com.konradpekala.blefik.ui.main.adapters.MainFragmentsAdapter
import com.konradpekala.blefik.ui.main.rooms.RoomsFragment
import com.konradpekala.blefik.ui.main.rooms.RoomsMvp
import com.konradpekala.blefik.ui.main.rooms.RoomsPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_change_name.view.*

class MainActivity : BaseActivity(),MainMvp.View {

    private lateinit var mRoomsFragment: RoomsFragment
    private lateinit var mRankingFragment: Fragment
    private lateinit var mFragmentAdapter: MainFragmentsAdapter

    private lateinit var mPresenter: MainPresenter<MainMvp.View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRoomsFragment = RoomsFragment()
        mRankingFragment = Fragment()

        mPresenter = Injector.getMainPresenter(this,this)

        setSupportActionBar(toolbarMain)
        initTabsStuff()

        mPresenter.onCreate()
    }

    private fun initTabsStuff(){
        mFragmentAdapter = MainFragmentsAdapter(supportFragmentManager,this)
        viewPager.adapter = mFragmentAdapter
        tabLayoutMain.setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuChangeNick -> showChangeNameDialog()
            R.id.menuLogOut -> mPresenter.onLogOutClick()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showChangeNameDialog(){
        val customView = LayoutInflater.from(this).inflate(R.layout.dialog_change_name,layoutMain,false)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Zmiana nicku")
            .setView(customView).create()

        customView.buttonDialogCreate.setOnClickListener {
            mPresenter.onChangeNickClick(customView.fieldUserName.text.toString())
            dialog.hide()
        }

        dialog.show()

    }

    override fun openGameActivity(room: Room) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("roomId",room.roomId)
        intent.putExtra("roomName",room.name)
        intent.putExtra("creatorId",room.name)
        startActivity(intent)
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
