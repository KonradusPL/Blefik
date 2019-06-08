package com.konradpekala.blefik.ui.login

import android.content.Intent
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import com.konradpekala.blefik.R
import com.konradpekala.blefik.injection.Injector
import com.konradpekala.blefik.ui.BlefikApplication
import com.konradpekala.blefik.ui.base.BaseActivity
import com.konradpekala.blefik.ui.main.MainActivity
import com.llollox.androidtoggleswitch.widgets.ToggleSwitch
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity(),LoginMvp.View {

    @Inject
    lateinit var mPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        mPresenter.onAttach(this)

        setContentView(R.layout.activity_login)

        initButtons()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.dispose()
    }

    private fun initButtons(){
        buttonSignUp.setOnClickListener {
            val email = fieldEmail.text.toString().trim()
            val nick = fieldNick.text.toString().trim()
            val password = fieldPassword.text.toString().trim()
            mPresenter.onSignUpButtonClick(email,password,nick)
        }
        buttonSignIn.setOnClickListener {
            mPresenter.onSignInButtonClick(fieldEmail.text.toString().trim(),fieldPassword.text.toString().trim())
        }

        switchLoginType.setCheckedPosition(0)
        switchLoginType.onChangeListener = object: ToggleSwitch.OnChangeListener{
            override fun onToggleSwitchChanged(position: Int) {
                TransitionManager.beginDelayedTransition(layoutLogin)
                fieldNick.visibility = if (position == 0) View.VISIBLE else View.GONE
            }
        }
    }

    override fun showLoading() {
        progressBarLogin.visibility = View.VISIBLE
        fieldEmail.isEnabled = false
        fieldNick.isEnabled = false
        fieldPassword.isEnabled = false
    }

    override fun hideLoading() {
        progressBarLogin.visibility = View.GONE
        fieldEmail.isEnabled = true
        fieldNick.isEnabled = true
        fieldPassword.isEnabled = true
    }

    override fun openMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
