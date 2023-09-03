package com.example.popmate.view.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.fragment.app.Fragment
import com.example.popmate.R
import com.example.popmate.view.fragments.HomeFragment
import com.example.popmate.view.fragments.MyPageFragment
import com.example.popmate.view.fragments.PopupStoreFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Stack

class MainActivity : AppCompatActivity() {
    private val fragmentStack: Stack<Fragment> = Stack()
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_home -> setCurrentFragment(HomeFragment(), false)
                R.id.page_popupstore -> setCurrentFragment(PopupStoreFragment(), false)
                R.id.page_mypage -> setCurrentFragment(MyPageFragment(), false)
            }
            true
        }

        setCurrentFragment(HomeFragment(), false)
    }

    private fun setCurrentFragment(fragment: Fragment, isBackPressed: Boolean) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            if (!fragmentStack.empty() && isBackPressed) {
                fragmentStack.pop()
            } else {
                addToBackStack(null)
                fragmentStack.push(fragment)
            }

            commit()
        }
    }

    fun goBack() {
        onBackPressed()
    }

    override fun onBackPressed() {

        if (fragmentStack.size >= 1) {
            fragmentStack.pop()
            val previousFragment = fragmentStack.peek()
            setCurrentFragment(previousFragment, true)

            when (previousFragment) {
                is HomeFragment -> bottomNavigationView.selectedItemId = R.id.page_home
                is PopupStoreFragment -> bottomNavigationView.selectedItemId = R.id.page_popupstore
                is MyPageFragment -> bottomNavigationView.selectedItemId = R.id.page_mypage
            }

            // 뒤로 가기를 눌렀을때 바텀 바 아이템 클릭 변경 시도
//            setCurrentFragment(previousFragment)
//            bottomNavigationView.selectedItemId = R.id.page_home
//            bottomNavigationView.findViewById<View>(R.id.page_home).performClick()
//            bottomNavigationView.performContextClick(335F, 847F)
//            bottomNavigationView.menu.findItem(R.id.page_home).performClick()
//            bottomNavigationView.findViewById<View>(R.id.page_mypage).performClick()
//            bottomNavigationView.getMenu().findItem(R.id.page_home).setChecked(true);
//            bottomNavigationView.menu.performIdentifierAction(R.id.page_home, 0)
        } else {

            super.onBackPressed()
        }
    }
}
