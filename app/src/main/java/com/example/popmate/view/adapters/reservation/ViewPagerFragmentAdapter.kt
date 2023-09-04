package com.example.popmate.view.adapters.reservation

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.popmate.view.activities.reservation.MyReservationActivity
import com.example.popmate.view.fragments.reservation.PostReservationFragment
import com.example.popmate.view.fragments.reservation.PreReservationFragment

class ViewPagerFragmentAdapter(myReservationActivity: MyReservationActivity): FragmentStateAdapter(myReservationActivity) {

    // 1. ViewPager2에 연결할 Fragment 들을 생성
    private val fragmentList = listOf(PreReservationFragment(), PostReservationFragment())

    // 2. ViesPager2에서 노출시킬 Fragment 의 갯수 설정
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    // 3. ViewPager2의 각 페이지에서 노출할 Fragment 설정
    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}
