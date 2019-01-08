package com.konradpekala.blefik.ui.room.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.konradpekala.blefik.R
import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.data.model.Status
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
                progressRoom.visibility = if(room.isChoosenByPlayer)
                    View.VISIBLE else View.GONE

                buttonStartGame.visibility = if(room.isChoosenByPlayer && room.locallyCreated)
                    View.VISIBLE else View.GONE

                val drawable = IconicsDrawable(context)
                    .icon(FontAwesome.Icon.faw_user)
                    .sizeDp(18)
                    .color(Color.BLACK)
                textUsersCount.setCompoundDrawables(null,null,drawable,null)

                layoutRoom.setOnClickListener {
                    Log.d("onRoomClick","inClickListener")
                    mvpView.getPresenter().onRoomClick(room)
                }

                buttonStartGame.setOnClickListener {
                    mvpView.getPresenter().onStartGameClick(room)
                }
            }
        }
    }

    fun showRoomLoading(room: Room){
        Log.d("showRoomLoading",room.isChoosenByPlayer.toString())
        for (item in rooms){
            val i = rooms.indexOf(item)
            if(item.roomId == room.roomId){
                Log.d("showRoomLoading","why")
                rooms[i].isChoosenByPlayer = true
                notifyItemChanged(i)
            }else if (item.isChoosenByPlayer){
                rooms[i].isChoosenByPlayer = false
                notifyItemChanged(i)
            }
        }
    }

    fun removeRoomsLoading(){
        for(room in rooms){
            if(room.isChoosenByPlayer){
                val itemIndex = rooms.indexOf(room)
                room.isChoosenByPlayer = false
                notifyItemChanged(itemIndex)
            }
        }
    }



    fun updateRooms(newRoom: Room){
        Log.d("updateRooms",newRoom.toString())

        var itemFound = false
        for (room in rooms){
            if(room.roomId == newRoom.roomId){
                itemFound = true
                val index = rooms.indexOf(room)
                if(newRoom.status == Status.Removed || newRoom.started){
                    rooms.remove(room)
                    notifyItemRemoved(index)
                }else if(newRoom.status == Status.Changed){
                    rooms[index] = newRoom
                    notifyItemChanged(index)
                }
            }
        }
        if(!itemFound && !newRoom.started){
            rooms.add(newRoom)
            notifyItemInserted(rooms.size-1)
        }

    }
}