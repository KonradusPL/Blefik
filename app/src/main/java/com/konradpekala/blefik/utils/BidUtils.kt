package com.konradpekala.blefik.utils

import com.konradpekala.blefik.data.model.Bid
import com.konradpekala.blefik.data.model.BidPickingType
import com.konradpekala.blefik.data.model.CardColor
import com.konradpekala.blefik.data.model.CardNumber

object BidUtils {

    fun isBidProperlyCreated(bid: Bid): Boolean{
        if(bid.pickingType == BidPickingType.OneCard && bid.firstCardNumber != CardNumber.None)
            return true
        else if (bid.pickingType == BidPickingType.TwoCards &&
            bid.firstCardNumber != CardNumber.None &&
            bid.secondCardNumber != CardNumber.None &&
            bid.firstCardNumber.name != bid.secondCardNumber.name)
            return true
        else if (bid.pickingType == BidPickingType.Color &&
            bid.color != CardColor.None)
            return true
        else if(bid.pickingType == BidPickingType.Set &&
            bid.firstCardNumber != CardNumber.None)
            return true
        return false
    }

    fun isNewBidHigher(newBid: Bid, oldBid: Bid?):Boolean{
        if (oldBid == null)
            return true

        if(newBid.power > oldBid.power)
            return true
        else if(newBid.power == oldBid.power
            && newBid.firstCardNumber.ordinal > oldBid.firstCardNumber.ordinal)
            return true
        return false
    }
}