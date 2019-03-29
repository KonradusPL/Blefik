package com.konradpekala.blefik.ui.main.rooms

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.konradpekala.blefik.R
import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.injection.Injector
import com.konradpekala.blefik.ui.base.BaseActivity
import com.konradpekala.blefik.ui.game.GameActivity
import com.konradpekala.blefik.ui.main.adapters.RoomsAdapter
import kotlinx.android.synthetic.main.activity_rooms.*
import kotlinx.android.synthetic.main.dialog_add_room.view.*
// Old not used activity replaced by RoomsFragment
class RoomsActivity : BaseActivity(), RoomsMvp.View {

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

    private fun initList() {
        mRoomsAdapter = RoomsAdapter(ArrayList(), this)
        listRooms.adapter = mRoomsAdapter
        listRooms.layoutManager = LinearLayoutManager(this)
    }


    override fun getListAdapter(): RoomsAdapter {
        return mRoomsAdapter
    }

    override fun openGameActivity(room: Room) {
        val intent = Intent(this,GameActivity::class.java)
        intent.putExtra("roomId",room.roomId)
        intent.putExtra("roomName",room.name)
        intent.putExtra("creatorId",room.name)
        startActivity(intent)
        finish()
    }

    override fun getPresenter(): RoomsMvp.Presenter<RoomsMvp.View> {
        return mPresenter
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
