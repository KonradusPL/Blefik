package com.konradpekala.blefik.ui.game.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.konradpekala.blefik.R
import com.konradpekala.blefik.data.model.Card
import com.konradpekala.blefik.data.model.CardColor
import com.mikepenz.community_material_typeface_library.CommunityMaterial
import com.mikepenz.iconics.IconicsDrawable
import kotlinx.android.synthetic.main.item_current_card.view.*

class CardsAdapter(val cards: ArrayList<Card>, val context: Context): RecyclerView.Adapter<CardsAdapter.CardViewHolder>() {
    override fun getItemCount() = cards.size

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(cards[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view =  LayoutInflater.from(context)
            .inflate(R.layout.item_current_card,parent,false)
        return CardViewHolder(view)
    }

    class CardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(card: Card){
            itemView.apply {
                val icon = when(card.color){
                    CardColor.Club -> CommunityMaterial.Icon.cmd_cards_club
                    CardColor.Diamond -> CommunityMaterial.Icon.cmd_cards_diamond
                    CardColor.Spade -> CommunityMaterial.Icon.cmd_cards_spade
                    CardColor.Heart -> CommunityMaterial.Icon.cmd_cards_heart
                    CardColor.None -> CommunityMaterial.Icon.cmd_cards_playing_outline
                }
                val color = when(card.color){
                    CardColor.Club -> Color.BLACK
                    CardColor.Diamond -> Color.RED
                    CardColor.Spade -> Color.BLACK
                    CardColor.Heart -> Color.RED
                    CardColor.None -> Color.BLACK
                }
                textNumber1.text = card.fromNumberToString()
                textNumber2.text = card.fromNumberToString()
                textNumber1.setTextColor(color)
                textNumber2.setTextColor(color)

                val drawable = IconicsDrawable(context)
                    .icon(icon)
                    .sizeDp(18)
                    .color(color)

                iconBadge1.setImageDrawable(drawable)
                iconBadge2.setImageDrawable(drawable)

            }
        }
    }
    fun refreshCards(list: List<Card>) {
        cards.clear()
        cards.addAll(list)
        notifyDataSetChanged()
    }
}