package com.konradpekala.blefik.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.konradpekala.blefik.R
import com.konradpekala.blefik.ui.base.BaseActivity
import com.konradpekala.blefik.ui.login.LoginActivity
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.dialog_update_user_value.view.*
import android.app.Activity
import android.app.Dialog
import android.util.Log
import android.view.View
import com.konradpekala.blefik.utils.schedulers.ValueToUpdate
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
            showUpdateUserValueDialog(ValueToUpdate.NICK)
        }
        buttonChangeEmail.setOnClickListener {
            showUpdateUserValueDialog(ValueToUpdate.EMAIL)
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

    private fun showUpdateUserValueDialog(type: ValueToUpdate){
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_update_user_value,layoutProfile,false)

        val dialog = if(type == ValueToUpdate.NICK) createNickDialog(view)
        else createEmailDialog(view)

        dialog.show()
    }

    private fun createNickDialog(view: View): Dialog{
        val dialog = createDialog(view, R.string.changing_nick,R.string.new_nick,R.string.update_nick)

        view.buttonDialogCreate.setOnClickListener {
            mPresenter.onChangeNickClick(view.fieldUserValue.text.toString())
            dialog.hide()
        }
        return dialog
    }

    private fun createEmailDialog(view: View): Dialog{
        val dialog = createDialog(view,R.string.changing_email,R.string.new_email,R.string.update_email)

        view.buttonDialogCreate.setOnClickListener {
            mPresenter.onChangeEmailClick(view.fieldUserValue.text.toString())
            dialog.hide()
        }

        return dialog
    }

    private fun createDialog(view: View,titleId: Int, hintId: Int, buttonTextId: Int): Dialog{
        view.fieldUserValue.setHint(hintId)
        view.buttonDialogCreate.setText(buttonTextId)

        return AlertDialog.Builder(this)
            .setTitle(titleId)
            .setView(view)
            .create()
    }

    override fun openLoginActivity() {
        val intent = Intent(this,LoginActivity::class.java)
        setResult(Activity.RESULT_OK,intent)
        startActivity(Intent(this,LoginActivity::class.java))
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
