package com.konradpekala.blefik

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.konradpekala.blefik.data.preferences.SharedPrefs
import com.konradpekala.blefik.ui.createProfile.CreateProfileActivity
import com.konradpekala.blefik.ui.room.RoomsActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferences = SharedPrefs(this)

        if(preferences.isUserLoggedIn())
            openRoomActivity()
        else
            openCreateProfileActivity()

    }
    private fun openRoomActivity(){
        startActivity(Intent(this,RoomsActivity::class.java))
        finish()
    }
    private fun openCreateProfileActivity(){
        startActivity(Intent(this,CreateProfileActivity::class.java))
        finish()
    }
}
