package com.konradpekala.blefik

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.konradpekala.blefik.data.auth.FirebaseAuth
import com.konradpekala.blefik.data.preferences.SharedPrefs
import com.konradpekala.blefik.ui.createProfile.CreateProfileActivity
import com.konradpekala.blefik.ui.login.LoginActivity
import com.konradpekala.blefik.ui.main.MainActivity
import es.dmoral.toasty.Toasty

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Toasty.Config.getInstance()
            .setInfoColor(ContextCompat.getColor(this,R.color.colorAccent))
            .setTextColor(Color.WHITE)
            .apply()

        val preferences = SharedPrefs(this)

        val authFirebase = FirebaseAuth(com.google.firebase.auth.FirebaseAuth.getInstance())
        val isUserLoggedIn = authFirebase.isUserLoggedIn()

        if(isUserLoggedIn)
            openMainActivity()
        else
            openLoginActivity()

    }
    private fun openMainActivity(){
        Log.d("openMainActivity",R.integer.main_activity_code.toString())
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
    private fun openLoginActivity(){
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }
}
