package com.konradpekala.blefik.ui.main.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.konradpekala.blefik.ui.main.MainActivity
import dagger.android.DaggerFragment

class MainFragmentsAdapter(fm: FragmentManager,val mainActivity: MainActivity): FragmentStatePagerAdapter(fm) {

    private val FRAGMENTS_COUNT = 2

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> mainActivity.getRoomsFragment()
            else -> mainActivity.getRankingFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Pokoje"
            else -> "Ranking"
        }
    }

    override fun getCount() = FRAGMENTS_COUNT
}