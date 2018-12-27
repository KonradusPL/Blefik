package com.konradpekala.blefik.ui.game.adapters.createbid

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.konradpekala.blefik.R
import com.konradpekala.blefik.data.model.BidPickingType
import com.konradpekala.blefik.data.model.BidType
import com.konradpekala.blefik.ui.game.GameMvp
import com.thoughtbot.expandablerecyclerview.MultiTypeExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder
import kotlinx.android.synthetic.main.item_bid_name.view.*

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
                    .inflate(R.layout.item_picker_card_color,parent,false)
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
        val viewType = getItemViewType(flatPosition)
        when (viewType){
            BidPickingType.Color.ordinal+5 -> {
                (holder as ColorViewHolder).bind()
            }
            BidPickingType.OneCard.ordinal+5 -> {
                (holder as OneCardViewHolder).bind()
            }
            BidPickingType.TwoCards.ordinal+5 -> {
                (holder as TwoCardsViewHolder).bind()
            }
            BidPickingType.Set.ordinal+5 -> {
                (holder as SetViewHolder).bind()
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
    internal class OneCardViewHolder(val root: View): ChildViewHolder(root){
        fun bind(){

        }
    }
    internal class TwoCardsViewHolder(val root: View): ChildViewHolder(root){
        fun bind(){

        }
    }
    internal class ColorViewHolder(val root: View): ChildViewHolder(root){
        fun bind(){

        }
    }
    internal class SetViewHolder(val root: View): ChildViewHolder(root){
        fun bind(){

        }

    }
}