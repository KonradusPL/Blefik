package com.konradpekala.blefik.ui.room.adapters

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.konradpekala.blefik.R
import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.ui.base.MvpView
import com.konradpekala.blefik.ui.room.RoomsMvp
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import com.mikepenz.iconics.IconicsDrawable
import kotlinx.android.synthetic.main.item_room.view.*


class RoomsAdapter(val rooms: ArrayList<Room>, val mvpView: RoomsMvp.View)
    : RecyclerView.Adapter<RoomsAdapter.RoomViewHolder>() {
    override fun getItemCount() = rooms.size

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.bind(rooms[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val view =  LayoutInflater.from(mvpView.getCtx())
            .inflate(R.layout.item_room,parent,false)
        return RoomViewHolder(view)
    }

    inner class RoomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(room: Room){
            itemView.apply {
                textRoomName.text = room.name
                textUsersCount.text = room.players.size.toString()
                progressRoom.visibility = if(room.isLoading)
                    View.VISIBLE else View.GONE

                val drawable = IconicsDrawable(context)
                    .icon(FontAwesome.Icon.faw_user)
                    .sizeDp(18)
                    .color(Color.BLACK)
                textUsersCount.setCompoundDrawables(null,null,drawable,null)

                itemView.setOnClickListener {
                    mvpView.getPresenter().onRoomClick(room)
                }
            }
        }
    }

    fun showRoomLoading(room: Room){
        Log.d("showRoomLoading",room.isLoading.toString())
        for (item in rooms){
            val i = rooms.indexOf(item)
            if(item.name == room.name && item.creatorId == room.creatorId){
                Log.d("showRoomLoading","why")
                rooms[i].isLoading = true
                notifyItemChanged(i)
            }else if (item.isLoading){
                rooms[i].isLoading = false
                notifyItemChanged(i)
            }
        }
    }



    fun updateList(room: Room){
        if(!room.removed){
            rooms.add(room)
            notifyItemInserted(rooms.size-1)
        }
    }
}