package com.konradpekala.blefik.utils

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import androidx.core.content.ContextCompat.getSystemService
import android.telephony.TelephonyManager



class PhoneStuff(val context: Context) {
    @SuppressLint("MissingPermission")
    fun getAndroidId(): String{
        return Settings.Secure.getString(context.contentResolver,
            Settings.Secure.ANDROID_ID)
    }
}