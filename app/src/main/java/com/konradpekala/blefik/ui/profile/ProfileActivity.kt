package com.konradpekala.blefik.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.konradpekala.blefik.R
import com.konradpekala.blefik.data.repo.profile.ProfileRepositoryImpl
import com.konradpekala.blefik.data.repo.profile.RemoteProfileRepository
import com.konradpekala.blefik.injection.Injector
import com.konradpekala.blefik.ui.base.BaseActivity
import com.konradpekala.blefik.ui.main.MainMvp
import com.konradpekala.blefik.ui.main.MainPresenter

class ProfileActivity : BaseActivity(),ProfileMvp.View {

    private lateinit var mPresenter: ProfilePresenter<ProfileMvp.View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        mPresenter = Injector.getProfilePresenter(this,this)

    }

    override fun openLoginActivity() {
    }

    override fun changeNick(nick: String) {
    }
}
