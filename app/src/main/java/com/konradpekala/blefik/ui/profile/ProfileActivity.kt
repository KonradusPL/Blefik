package com.konradpekala.blefik.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.konradpekala.blefik.R
import com.konradpekala.blefik.data.repo.profile.ProfileRepositoryImpl
import com.konradpekala.blefik.data.repo.profile.RemoteProfileRepository
import com.konradpekala.blefik.injection.Injector
import com.konradpekala.blefik.ui.base.BaseActivity
import com.konradpekala.blefik.ui.main.MainMvp
import com.konradpekala.blefik.ui.main.MainPresenter
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.dialog_change_name.view.*

class ProfileActivity : BaseActivity(),ProfileMvp.View {

    private lateinit var mPresenter: ProfilePresenter<ProfileMvp.View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        mPresenter = Injector.getProfilePresenter(this,this)

        initButtons()

        mPresenter.onCreate()
    }

    private fun initButtons() {
        buttonChangeNick.setOnClickListener {
            showChangeNameDialog()
        }
    }

    private fun showChangeNameDialog(){
        val customView = LayoutInflater.from(this).inflate(R.layout.dialog_change_name,layoutProfile,false)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Zmiana nicku")
            .setView(customView).create()

        customView.buttonDialogCreate.setOnClickListener {
            mPresenter.onChangeNickClick(customView.fieldUserName.text.toString())
            dialog.hide()
        }

        dialog.show()

    }

    override fun openLoginActivity() {
    }

    override fun changeNick(nick: String) {
        textNickBig.text = nick
        textNickSmall.text = nick
    }
}
