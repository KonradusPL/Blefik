package com.konradpekala.blefik.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.konradpekala.blefik.R
import com.konradpekala.blefik.injection.Injector
import com.konradpekala.blefik.ui.base.BaseActivity
import com.konradpekala.blefik.ui.login.LoginActivity
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.dialog_change_name.view.*
import android.app.Activity
import android.util.Log
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection
import java.io.File
import javax.inject.Inject


class ProfileActivity : BaseActivity(),ProfileMvp.View {

    @Inject
    lateinit var mPresenter: ProfilePresenter<ProfileMvp.View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_profile)

        mPresenter.onAttach(this)

        initButtons()

        mPresenter.onCreate()
    }

    private fun initButtons() {
        buttonChangeNick.setOnClickListener {
            showChangeNameDialog()
        }
        buttonLogOut.setOnClickListener{
            mPresenter.onLogOutClick()
        }
        imageProfile.setOnClickListener {
            CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start(this)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("onStart","true")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri = result.uri
                mPresenter.onNewImageChosen(resultUri.path!!)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
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
        startActivity(Intent(this,LoginActivity::class.java))
        finishActivity()
        finish()
    }

    override fun changeNick(nick: String) {
        textNickBig.text = nick
        textNickSmall.text = nick
    }

    override fun changeEmail(email: String) {
        textEmail.text = email
    }

    override fun changeProfileImage(file: File) {
        Picasso.get()
            .load(file)
            .placeholder(R.drawable.user_holder)
            .resize(100,100)
            .centerCrop()
            .into(imageProfile)
    }
    override fun changeProfileImage(uri: String) {
        Picasso.get()
            .load(uri)
            .placeholder(R.drawable.user_holder)
            .resize(100,100)
            .centerCrop()
            .into(imageProfile)
    }
}
