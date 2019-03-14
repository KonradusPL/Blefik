package com.konradpekala.blefik.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.konradpekala.blefik.R
import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.injection.Injector
import com.konradpekala.blefik.ui.base.BaseFragment
import com.konradpekala.blefik.ui.game.GameActivity
import com.konradpekala.blefik.ui.main.adapters.RoomsAdapter
import kotlinx.android.synthetic.main.activity_rooms.*
import kotlinx.android.synthetic.main.dialog_add_room.view.*

class RoomsFragment: BaseFragment<MainMvp.View>(),RoomsMvp.View {

    private lateinit var mRoomsAdapter: RoomsAdapter
    private lateinit var mPresenter: RoomsPresenter<RoomsMvp.View>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_rooms,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mPresenter = Injector.getRoomPresenter(this,parentContext)

        initList()
        initUI()

        mPresenter.start()
    }

    private fun initUI(){
        buttonAddRoom.setOnClickListener {
            mPresenter.onAddRoomClick()
        }
    }

    private fun initList() {
        mRoomsAdapter = RoomsAdapter(ArrayList(), this)
        listRooms.adapter = mRoomsAdapter
        listRooms.layoutManager = LinearLayoutManager(parentContext)
    }

    override fun getListAdapter(): RoomsAdapter {
        return mRoomsAdapter
    }

    override fun openGameActivity(room: Room) {
        parentMvp.openGameActivity()
    }

    override fun showCreateRoomView() {
        val customView = LayoutInflater.from(parentContext).inflate(R.layout.dialog_add_room,constraintRooms,false)

        val dialog = AlertDialog.Builder(parentContext)
            .setTitle("Stwórz nowy pokój")
            .setView(customView).create()

        dialog.show()

        customView.buttonDialogCreate.setOnClickListener {
            mPresenter.onAddRoomClick(customView.fieldRoomName.text.toString())
            dialog.hide()
        }
    }

    override fun getPresenter(): RoomsMvp.Presenter<RoomsMvp.View> = mPresenter

}