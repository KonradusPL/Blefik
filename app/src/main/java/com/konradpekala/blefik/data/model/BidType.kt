package com.konradpekala.blefik.data.model

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

data class BidType(val name: String, val pickingType: BidPickingType, val power: Int,
                   val array: ArrayList<BidValue> )
    :ExpandableGroup<BidValue>(name,array)

enum class BidPickingType{Color,OneCard,TwoCards,Set}