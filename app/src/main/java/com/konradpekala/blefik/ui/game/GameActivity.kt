package com.konradpekala.blefik.ui.game

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.*
import com.konradpekala.blefik.R
import com.konradpekala.blefik.ui.base.BaseActivity
import com.konradpekala.blefik.injection.Injector
import com.konradpekala.blefik.ui.game.adapters.CardsAdapter
import com.konradpekala.blefik.ui.game.adapters.PlayersAdapter
import com.konradpekala.blefik.ui.game.adapters.createbid.BidsAdapter
import com.konradpekala.blefik.ui.main.MainActivity
import com.konradpekala.blefik.utils.CardsStuff
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import com.mikepenz.iconics.IconicsDrawable
import com.transitionseverywhere.Rotate
import kotlinx.android.synthetic.main.activity_game.*
import org.jetbrains.anko.ctx

class GameActivity : BaseActivity(),GameMvp.View {

    private lateinit var mPresenter: GamePresenter<GameMvp.View>

    private lateinit var mBidAdapter: CardsAdapter
    private lateinit var mPlayerCardsAdapter: CardsAdapter
    private lateinit var mPlayersAdapter: PlayersAdapter

    private var playerCardsViewOpened = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        mPresenter = Injector.getGamePresenter(this,ctx)

        toolbarGame.title = intent.getStringExtra("roomName") ?: ""
        setSupportActionBar(toolbarGame)

        initBidList()
        initPlayerCardsList()
        initPlayersList()
        initCardsShowingButton()
        initBidsCreatorList()
        initOtherUI()

        val roomId = intent.getStringExtra("roomId")
        val creatorId = intent.getStringExtra("creatorId")
        Log.d("onCreate","roomId: $roomId")

        val shouldGameStart = savedInstanceState?.getBoolean("shouldGameStart",true) ?: true
        Log.d("onCreate","shouldGameStart: " + shouldGameStart.toString())
        //**
        //****
        //******
        mPresenter.startGame(roomId,creatorId,shouldGameStart)
        //******
        //****
        //**
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("shouldGameStart",false)
    }

    private fun initOtherUI(){
        iconBack.setImageDrawable(IconicsDrawable(this)
            .icon(FontAwesome.Icon.faw_arrow_down)
            .sizeDp(18)
            .color(Color.BLACK))
        iconBack.setOnClickListener {
            closeBidCreator()
            playerCardsViewOpened = false
        }
    }

    private fun initCardsShowingButton(){
        val iconicsDrawable = IconicsDrawable(this)
            .icon(FontAwesome.Icon.faw_arrow_up)
            .sizeDp(18)
            .color(Color.WHITE)

        fabShowPlayerCards.setImageDrawable(iconicsDrawable)

        var visible = false

        val constraintSet1 = ConstraintSet()
        constraintSet1.clone(constrLayoutGame)
        val constraintSet2 = ConstraintSet()
        constraintSet2.clone(constrLayoutGame)
        constraintSet2.connect(R.id.layoutPlayerCards,ConstraintSet.BOTTOM,
            ConstraintLayout.LayoutParams.PARENT_ID,ConstraintSet.BOTTOM)
        constraintSet2.clear(R.id.layoutPlayerCards,ConstraintSet.TOP)

        fabShowPlayerCards.setOnClickListener {

            visible = !visible


            TransitionManager.beginDelayedTransition(constrLayoutGame,TransitionSet()
                .addTransition(Rotate())
                .addTransition(ChangeBounds()))
            val constraint = if (visible) constraintSet2 else constraintSet1
            constraint.applyTo(constrLayoutGame)
            fabShowPlayerCards.rotation = if (visible) 180f else 0f
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_game,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item != null && item.itemId == R.id.item_refresh){
            showMessage("refreshGame")
            mPresenter.refreshGame()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initBidsCreatorList(){
        val bidTypes = CardsStuff.generateBidTypesForCreator()
        val mBidsAdapter = BidsAdapter(bidTypes,this)

        listBids.adapter = mBidsAdapter
        listBids.layoutManager = LinearLayoutManager(this)
    }


    private fun initPlayersList(){
        mPlayersAdapter = PlayersAdapter(ArrayList(),this)
        listUsers.adapter = mPlayersAdapter
        listUsers.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
    }

    private fun initBidList(){
        mBidAdapter = CardsAdapter(ArrayList(), this)
        listCurrentBid.adapter = mBidAdapter
        listCurrentBid.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL,false)
    }

    private fun initPlayerCardsList(){
        mPlayerCardsAdapter = CardsAdapter(ArrayList(), this)
        listCurrentCards.adapter = mPlayerCardsAdapter
        listCurrentCards.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL,false)
    }

    override fun getPresenter(): GameMvp.Presenter<GameMvp.View> {
        return mPresenter
    }

    override fun getBidAdapter(): CardsAdapter {
        return mBidAdapter
    }

    override fun getPlayersAdapter(): PlayersAdapter {
        return mPlayersAdapter
    }

    override fun animateBidChanges() {
        TransitionManager.beginDelayedTransition(constrLayoutGame)
    }

    override fun getPlayerCardsAdapter(): CardsAdapter {
        return mPlayerCardsAdapter
    }

    override fun openBidCreator() {
        TransitionManager.beginDelayedTransition(constrLayoutGame,Slide(Gravity.BOTTOM))
        layoutBidCreator.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle("Wyjście z gry")
            .setMessage("Jesteś pewien że chcesz wyjść z gry?")
            .setPositiveButton("Tak!") { _, _ ->
                openRoomActivity()
            }.setNegativeButton("Nigdy(͡°͜ʖ͡°)"){ _, _ ->  }
            .create()
            .show()
    }

    override fun openRoomActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun closeBidCreator(){
        TransitionManager.beginDelayedTransition(constrLayoutGame,Slide(Gravity.BOTTOM))
        layoutBidCreator.visibility = View.GONE
    }

}
