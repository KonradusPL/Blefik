package com.konradpekala.blefik.ui.createProfile


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.View
import com.konradpekala.blefik.R
import com.konradpekala.blefik.data.database.FirebaseDatabase
import com.konradpekala.blefik.data.model.User
import com.konradpekala.blefik.data.preferences.SharedPreferences
import com.konradpekala.blefik.data.repo.CreateProfileRepo
import com.konradpekala.blefik.ui.base.BaseActivity
import com.konradpekala.blefik.ui.room.RoomsActivity
import kotlinx.android.synthetic.main.activity_create_profile.*


class CreateProfileActivity : BaseActivity(),CreateProfileMvp.View {

    private lateinit var mPresenter:CreateProfilePresenter<CreateProfileMvp.View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile)

        mPresenter = CreateProfilePresenter(this, CreateProfileRepo(FirebaseDatabase(),
            SharedPreferences(applicationContext)))

        initUI()
    }

    private fun initUI(){
        buttonCreateProfile.setOnClickListener {
            mPresenter.onAddUserClick(fieldNick.text.toString())
        }
    }

    override fun showLoading() {
        progressCreateUser.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressCreateUser.visibility = View.GONE
    }

    override fun openRoomActivity() {
        startActivity(Intent(this,RoomsActivity::class.java))
        finish()
    }
}
