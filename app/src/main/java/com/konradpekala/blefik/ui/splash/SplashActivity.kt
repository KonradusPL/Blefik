package com.konradpekala.blefik.ui.splash

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.konradpekala.blefik.R
import com.konradpekala.blefik.data.auth.FirebaseUserSession
import com.konradpekala.blefik.data.auth.UserSession
import com.konradpekala.blefik.data.preferences.SharedPrefs
import com.konradpekala.blefik.ui.login.LoginActivity
import com.konradpekala.blefik.ui.main.MainActivity
import dagger.android.AndroidInjection
import es.dmoral.toasty.Toasty
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var mUserSession: UserSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        applyToastsConfig()

        if(mUserSession.isUserLoggedIn())
            openMainActivity()
        else
            openLoginActivity()

    }

    private fun applyToastsConfig() {
        Toasty.Config.getInstance()
            .setInfoColor(ContextCompat.getColor(this, R.color.colorAccent))
            .setTextColor(Color.WHITE)
            .apply()
    }

    private fun openMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
    private fun openLoginActivity(){
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }
}
