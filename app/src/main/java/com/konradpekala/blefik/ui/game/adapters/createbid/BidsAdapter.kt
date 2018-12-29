package com.konradpekala.blefik.ui.game.adapters.createbid

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.rotationMatrix
import com.konradpekala.blefik.R
import com.konradpekala.blefik.data.model.*
import com.konradpekala.blefik.ui.game.GameMvp
import com.mikepenz.community_material_typeface_library.CommunityMaterial
import com.mikepenz.iconics.IconicsDrawable
import com.thoughtbot.expandablerecyclerview.MultiTypeExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder
import kotlinx.android.synthetic.main.item_bid_name.view.*
import kotlinx.android.synthetic.main.item_picker_color.view.*
import kotlinx.android.synthetic.main.item_picker_one_card.view.*
import kotlinx.android.synthetic.main.item_picker_set.view.*
import kotlinx.android.synthetic.main.item_picker_two_cards.view.*

internal class BidsAdapter(val bidTypes: ArrayList<BidType>,val viewMvp: GameMvp.View): MultiTypeExpandableRecyclerViewAdapter
<BidsAdapter.FatherViewHolder, ChildViewHolder>(bidTypes) {

    override fun getChildViewType(position: Int, group: ExpandableGroup<*>?, childIndex: Int): Int {
        return (group as BidType).pickingType.ordinal + 5
    }

    override fun onCreateGroupViewHolder(parent: ViewGroup?, viewType: Int): FatherViewHolder {
        Log.d("onCreateGroupViewHolder",viewType.toString())
            val itemView = LayoutInflater.from(viewMvp.getCtx())
                .inflate(R.layout.item_bid_name,parent,false)
        return FatherViewHolder(itemView)
    }

    override fun isChild(viewType: Int): Boolean {
        return viewType == BidPickingType.Color.ordinal +5||
                viewType == BidPickingType.Set.ordinal +5||
                viewType == BidPickingType.OneCard.ordinal+5 ||
                viewType == BidPickingType.TwoCards.ordinal+5
    }

    override fun onCreateChildViewHolder(parent: ViewGroup?, viewType: Int): ChildViewHolder {
        when (viewType){
            BidPickingType.Color.ordinal+5 -> {
                val itemView = LayoutInflater.from(viewMvp.getCtx())
                    .inflate(R.layout.item_picker_color,parent,false)
                return ColorViewHolder(itemView)
            }
            BidPickingType.OneCard.ordinal+5 -> {
                val itemView = LayoutInflater.from(viewMvp.getCtx())
                    .inflate(R.layout.item_picker_one_card,parent,false)
                return OneCardViewHolder(itemView)
            }
            BidPickingType.TwoCards.ordinal+5 -> {
                val itemView = LayoutInflater.from(viewMvp.getCtx())
                    .inflate(R.layout.item_picker_two_cards,parent,false)
                return TwoCardsViewHolder(itemView)
            }
            BidPickingType.Set.ordinal +5-> {
                val itemView = LayoutInflater.from(viewMvp.getCtx())
                    .inflate(R.layout.item_picker_set,parent,false)
                return SetViewHolder(itemView)
            }
            else -> {
                throw Throwable("zły numerek")
            }
        }
    }

    override fun onBindChildViewHolder(holder: ChildViewHolder?, flatPosition: Int,
                                       group: ExpandableGroup<*>?, childIndex: Int) {
        val bidType = group as BidType
        val viewType = getItemViewType(flatPosition)
        when (viewType){
            BidPickingType.Color.ordinal+5 -> {
                (holder as ColorViewHolder).bind(bidType)
            }
            BidPickingType.OneCard.ordinal+5 -> {
                (holder as OneCardViewHolder).bind(bidType)
            }
            BidPickingType.TwoCards.ordinal+5 -> {
                (holder as TwoCardsViewHolder).bind(bidType)
            }
            BidPickingType.Set.ordinal+5 -> {
                (holder as SetViewHolder).bind(bidType)
            }
            else -> {
                throw Throwable("zły numerek")
            }
        }
    }

    override fun onBindGroupViewHolder(holder: FatherViewHolder?, flatPosition: Int, group: ExpandableGroup<*>?) {
        holder?.bind(group as BidType)
    }

    internal class FatherViewHolder(private val root: View): GroupViewHolder(root) {
        override fun onClick(v: View?) {
            super.onClick(v)
        }
        fun bind(bidType: BidType){
            root.textBidName.text = bidType.name
        }
    }
    inner class OneCardViewHolder(val root: View): ChildViewHolder(root){
        fun bind(bidType: BidType){
            root.buttonBidOneCreate.setOnClickListener {
                val text = root.fieldFirstCard.text.toString()
                val cardNumber = Card.fromStringToNumber(text)

                viewMvp.getPresenter().onCreateBidClick(Bid(bidType.name,bidType.power,bidType.pickingType,
                    CardColor.None,cardNumber,CardNumber.None))
            }

        }
    }
    inner class TwoCardsViewHolder(val root: View): ChildViewHolder(root){
        fun bind(bidType: BidType){
            root.buttonBidTwoCreate.setOnClickListener {
                val text1 = root.fieldFirstOfTwo.text.toString()
                val text2 = root.fieldSecondOfTwo.text.toString()
                val cardNumber1 = Card.fromStringToNumber(text1)
                val cardNumber2 = Card.fromStringToNumber(text2)

                viewMvp.getPresenter().onCreateBidClick(Bid(bidType.name,bidType.power,bidType.pickingType,
                    CardColor.None,cardNumber1,cardNumber2))
            }

        }
    }
     inner class ColorViewHolder(val root: View): ChildViewHolder(root){
        fun bind(bidType: BidType){
            var cardColor: CardColor = CardColor.None
            root.apply {
                val ctx = viewMvp.getCtx()
                buttonClub.setImageDrawable(IconicsDrawable(ctx).sizeDp(20)
                    .icon(CommunityMaterial.Icon.cmd_cards_club).color(Color.BLACK))
                buttonSpade.setImageDrawable(IconicsDrawable(ctx).sizeDp(20).icon(CommunityMaterial.Icon.cmd_cards_spade)
                    .color(Color.BLACK))
                buttonHeart.setImageDrawable(IconicsDrawable(ctx).sizeDp(20).icon(CommunityMaterial.Icon.cmd_cards_heart)
                    .color(Color.RED))
                buttonDiamond.setImageDrawable(IconicsDrawable(ctx).sizeDp(20).icon(CommunityMaterial.Icon.cmd_cards_diamond)
                    .color(Color.RED))

                buttonClub.setOnClickListener {
                    cardColor = CardColor.Club
                    cleanButtons()
                    buttonClub.setBackgroundColor(Color.LTGRAY)
                }
                buttonSpade.setOnClickListener {
                    cardColor = CardColor.Spade
                    cleanButtons()
                    buttonSpade.setBackgroundColor(Color.LTGRAY)
                }
                buttonHeart.setOnClickListener {
                    cardColor = CardColor.Heart
                    cleanButtons()
                    buttonHeart.setBackgroundColor(Color.LTGRAY)
                }
                buttonDiamond.setOnClickListener {
                    cardColor = CardColor.Diamond
                    cleanButtons()
                    buttonDiamond.setBackgroundColor(Color.LTGRAY)
                }

                buttonBidColorCreate.setOnClickListener {
                    val bid = Bid(bidType.name,bidType.power,bidType.pickingType,cardColor,
                        CardNumber.None,CardNumber.None)
                    viewMvp.getPresenter().onCreateBidClick(bid)
                }
            }
        }
         private fun cleanButtons(){
             root.apply {
                 buttonClub.setBackgroundColor(Color.WHITE)
                 buttonSpade.setBackgroundColor(Color.WHITE)
                 buttonHeart.setBackgroundColor(Color.WHITE)
                 buttonDiamond.setBackgroundColor(Color.WHITE)
             }
         }
    }
    inner class SetViewHolder(val root: View): ChildViewHolder(root){
        fun bind(bidType: BidType){
            root.apply {
                buttonBidSetCreate.setOnClickListener {
                    val text = root.fieldHighestCard.text.toString()
                    val cardNumber = if(text == "A") CardNumber.Ace
                    else CardNumber.King
                    viewMvp.getPresenter().onCreateBidClick(Bid(bidType.name,bidType.power,bidType.pickingType,
                        CardColor.None,cardNumber,CardNumber.None))
                }
            }
        }

    }
}