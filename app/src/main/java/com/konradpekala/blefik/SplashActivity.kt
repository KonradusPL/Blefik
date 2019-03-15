package com.konradpekala.blefik

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.konradpekala.blefik.data.preferences.SharedPrefs
import com.konradpekala.blefik.ui.createProfile.CreateProfileActivity
import com.konradpekala.blefik.ui.main.rooms.RoomsActivity
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
            openMainActivity()
        else
            openCreateProfileActivity()

    }
    private fun openMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
    private fun openCreateProfileActivity(){
        startActivity(Intent(this,CreateProfileActivity::class.java))
        finish()
    }
}
