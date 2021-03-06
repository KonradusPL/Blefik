package com.konradpekala.blefik.ui.game.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.konradpekala.blefik.R
import com.konradpekala.blefik.data.model.Player
import com.konradpekala.blefik.ui.game.GameMvp
import com.mikepenz.community_material_typeface_library.CommunityMaterial
import com.mikepenz.iconics.IconicsDrawable
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_user.view.*

class PlayersAdapter(val players: ArrayList<Player>, val mvpView: GameMvp.View): RecyclerView.Adapter<PlayersAdapter.UserVH>() {
    override fun getItemCount() = players.size

    override fun onBindViewHolder(holder: UserVH, position: Int) {
        holder.bind(players[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVH {
        val view =  LayoutInflater.from(mvpView.getCtx())
            .inflate(R.layout.item_user,parent,false)
        return UserVH(view)
    }

    inner class UserVH(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(player: Player){

            if (player.imageUrl.isNotEmpty())
                Picasso.get()
                    .load(player.imageUrl)
                    .placeholder(R.drawable.user_holder)
                    .resize(100,100)
                    .centerCrop()
                    .into(itemView.imageUser)

            (itemView as MaterialCardView).strokeColor = (if(player.isCurrentPlayer)
                ContextCompat.getColor(mvpView.getCtx(),R.color.colorPrimary)
            else Color.TRANSPARENT)
            itemView.apply {

                textFullName.text = player.nick
                textPlayerCards.text = "${player.currentCards.size}"

                val iconCardDrawable = IconicsDrawable(context)
                    .icon(CommunityMaterial.Icon.cmd_cards_playing_outline)
                    .color(Color.BLACK)
                    .sizeDp(14)
                textPlayerCards.setCompoundDrawables(null,null,iconCardDrawable,null)

                /*setBackgroundColor(if(player.isCurrentPlayer) ContextCompat.getColor(context,R.color.colorCurrentPlayer)
                else Color.WHITE)*/

                if(player.phoneOwner && player.isCurrentPlayer){
                    buttonCheck.visibility = View.VISIBLE
                    buttonRaiseBid.visibility = View.VISIBLE
                }else{
                    buttonCheck.visibility = View.GONE
                    buttonRaiseBid.visibility = View.GONE
                }

                buttonRaiseBid.setOnClickListener {
                    mvpView.getPresenter().onRaiseBidClick()
                }

                buttonCheck.setOnClickListener {
                    mvpView.getPresenter().onCheckBidClick()
                }

            }
        }
    }

    fun refresh(list: List<Player>) {
        players.clear()
        players.addAll(list)
        notifyDataSetChanged()
    }
}