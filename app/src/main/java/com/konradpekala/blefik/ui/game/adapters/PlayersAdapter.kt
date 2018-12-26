package com.konradpekala.blefik.ui.game.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.konradpekala.blefik.R
import com.konradpekala.blefik.data.model.Player
import kotlinx.android.synthetic.main.item_user.view.*

class PlayersAdapter(val players: ArrayList<Player>, val context: Context): RecyclerView.Adapter<PlayersAdapter.UserVH>() {
    override fun getItemCount() = players.size

    override fun onBindViewHolder(holder: UserVH, position: Int) {
        holder.bind(players[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVH {
        val view =  LayoutInflater.from(context)
            .inflate(R.layout.item_user,parent,false)
        return UserVH(view)
    }

    class UserVH(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(player: Player){
            itemView.apply {
                textFullName.text = player.nick
                textPlayerCards.text = "${player.currentCards.size} kart"
                setBackgroundColor(if(player.isCurrentPlayer) ContextCompat.getColor(context,R.color.colorCurrentPlayer)
                else Color.WHITE)

            }
        }
    }

    fun refresh(list: List<Player>) {
        players.clear()
        players.addAll(list)
        notifyDataSetChanged()
    }
}