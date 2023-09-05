package com.example.popmate.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.popmate.R
import com.example.popmate.view.fragments.popupstore.HomeFragment
//import com.example.popmate.view.fragments.MyPageFragment
import com.example.popmate.view.fragments.popupstore.PopupStoreFragment
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
//                R.id.page_mypage -> setCurrentFragment(MyPageFragment(), false)
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
//                is MyPageFragment -> bottomNavigationView.selectedItemId = R.id.page_mypage
            }
        } else {
            super.onBackPressed()
        }
    }
}
