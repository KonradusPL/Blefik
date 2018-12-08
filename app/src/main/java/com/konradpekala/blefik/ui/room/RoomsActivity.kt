package com.konradpekala.blefik.ui.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.konradpekala.blefik.R
import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.ui.base.BaseActivity
import com.konradpekala.blefik.ui.room.adapters.RoomsAdapter
import kotlinx.android.synthetic.main.activity_rooms.*

class RoomsActivity : BaseActivity(),RoomsMvp.View {

    private lateinit var mRoomsAdapter: RoomsAdapter
    private lateinit var mPresenter: RoomsPresenter<RoomsMvp.View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rooms)

        mPresenter = RoomsPresenter(this)

        initList()
        initUI()
    }

    private fun initUI(){
        buttonAddRoom.setOnClickListener {
            mPresenter.onAddRoomClick()
        }
    }

    private fun initList(){
        mRoomsAdapter = RoomsAdapter(ArrayList(),this)
        listRooms.adapter = mRoomsAdapter
        listRooms.layoutManager = LinearLayoutManager(this)
    }

    override fun showRooms(list: List<Room>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showCreateRoomView() {
        val customView = LayoutInflater.from(this).inflate(R.layout.dialog_add_room,constraintRooms,false)
        AlertDialog.Builder(this)
            .setTitle("Stwórz nowy pokój")
            .setView(customView).create()
            .show()
    }
}
