package com.konradpekala.blefik

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.konradpekala.blefik.data.preferences.SharedPrefs
import com.konradpekala.blefik.ui.createProfile.CreateProfileActivity
import com.konradpekala.blefik.ui.main.RoomsActivity
import es.dmoral.toasty.Toasty

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Toasty.Config.getInstance()
            .setInfoColor(ContextCompat.getColor(this,R.color.colorAccent))
            .setTextColor(Color.WHITE)
            .apply()

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
