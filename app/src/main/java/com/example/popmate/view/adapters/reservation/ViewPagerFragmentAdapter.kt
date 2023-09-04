package com.example.popmate.view.adapters.reservation

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.popmate.view.activities.reservation.MyReservationActivity
import com.example.popmate.view.fragments.reservation.CancelReservationFragment
import com.example.popmate.view.fragments.reservation.PostReservationFragment
import com.example.popmate.view.fragments.reservation.PreReservationFragment

class ViewPagerFragmentAdapter(myReservationActivity: MyReservationActivity) :
    FragmentStateAdapter(myReservationActivity) {

    private val fragmentList =
        listOf(PreReservationFragment(), PostReservationFragment(), CancelReservationFragment())

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}
