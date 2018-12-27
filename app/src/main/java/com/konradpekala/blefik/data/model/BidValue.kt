package com.konradpekala.blefik.data.model

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable

class BidValue(): Parcelable {
    var val1: String = ""
    var val2: String = ""

    @SuppressLint("ParcelClassLoader")
    constructor(parcel: Parcel) : this() {
        val bundle = parcel.readBundle()
        val1 = bundle.getString("val1")!!
        val2 = bundle.getString("val2")!!
    }

    constructor( val1: String,val2: String) : this() {
        this.val1 = val1
        this.val2 = val2

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        val bundle = Bundle()
        bundle.putString("val1",val1)
        bundle.putString("val2",val2)
        parcel.writeBundle(bundle)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BidValue> {
        override fun createFromParcel(parcel: Parcel): BidValue {
            return BidValue(parcel)
        }

        override fun newArray(size: Int): Array<BidValue?> {
            return arrayOfNulls(size)
        }
    }
}