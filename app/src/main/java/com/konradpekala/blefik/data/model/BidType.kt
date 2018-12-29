package com.konradpekala.blefik.data.model

import com.google.firebase.firestore.Exclude
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

data class BidType(val name: String,
                   val pickingType: BidPickingType,
                   val power: Int,
                   @get:Exclude val array: ArrayList<BidValue> )
    :ExpandableGroup<BidValue>(name,array)

enum class BidPickingType{Color,OneCard,TwoCards,Set}