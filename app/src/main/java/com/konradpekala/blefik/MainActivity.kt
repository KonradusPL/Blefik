package com.konradpekala.blefik

import android.content.Intent
import android.os.Bundle
import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.ui.base.BaseActivity
import com.konradpekala.blefik.ui.game.GameActivity
import com.konradpekala.blefik.ui.main.MainMvp
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(),MainMvp.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabMain.setupWithViewPager(viewPager)
    }

    override fun openGameActivity(room: Room) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("roomId",room.roomId)
        intent.putExtra("roomName",room.name)
        intent.putExtra("creatorId",room.name)
        startActivity(intent)
        finish()
    }
}
