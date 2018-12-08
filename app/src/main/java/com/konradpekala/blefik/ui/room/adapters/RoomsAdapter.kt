package com.konradpekala.blefik.ui.room.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.konradpekala.blefik.R
import com.konradpekala.blefik.data.model.Card
import com.konradpekala.blefik.data.model.Room
import kotlinx.android.synthetic.main.item_room.view.*


class RoomsAdapter(val rooms: ArrayList<Room>, val context: Context)
    : RecyclerView.Adapter<RoomsAdapter.RoomViewHolder>() {
    override fun getItemCount() = rooms.size

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.bind(rooms[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val view =  LayoutInflater.from(context)
            .inflate(R.layout.item_room,parent,false)
        return RoomViewHolder(view)
    }

    class RoomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(room: Room){
            itemView.apply {
                textRoomName.text = room.name
                textUsersCount.text = room.usersCount.toString()
            }
        }
    }

    fun updateList(room: Room){
        if(!room.isRemoved){
            rooms.add(room)
            notifyItemInserted(rooms.size-1)
        }
    }
}