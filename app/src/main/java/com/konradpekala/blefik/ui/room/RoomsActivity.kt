package com.konradpekala.blefik.ui.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.konradpekala.blefik.R
import com.konradpekala.blefik.data.database.FirebaseDatabase
import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.data.preferences.SharedPreferences
import com.konradpekala.blefik.data.repo.RoomsRepo
import com.konradpekala.blefik.injection.Injector
import com.konradpekala.blefik.ui.base.BaseActivity
import com.konradpekala.blefik.ui.room.adapters.RoomsAdapter
import kotlinx.android.synthetic.main.activity_rooms.*
import kotlinx.android.synthetic.main.dialog_add_room.*
import kotlinx.android.synthetic.main.dialog_add_room.view.*

class RoomsActivity : BaseActivity(),RoomsMvp.View {

    private lateinit var mRoomsAdapter: RoomsAdapter
    private lateinit var mPresenter: RoomsPresenter<RoomsMvp.View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rooms)

        mPresenter = Injector.getRoomPresenter(this,applicationContext)

        initList()
        initUI()

        mPresenter.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.stop()
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

    override fun updateRooms(room: Room) {
        mRoomsAdapter.updateList(room)
    }

    override fun showCreateRoomView() {
        val customView = LayoutInflater.from(this).inflate(R.layout.dialog_add_room,constraintRooms,false)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Stwórz nowy pokój")
            .setView(customView).create()

        dialog.show()

        customView.buttonDialogCreate.setOnClickListener {
            mPresenter.onAddRoomClick(customView.fieldRoomName.text.toString())
            dialog.hide()
        }
    }
}
