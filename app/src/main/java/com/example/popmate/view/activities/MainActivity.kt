package com.example.popmate.view.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.popmate.R
import com.example.popmate.config.BaseActivity
import com.example.popmate.databinding.ActivityMainBinding
import com.example.popmate.model.repository.ApiClient
import com.example.popmate.util.LessonLoginDialog
import com.example.popmate.view.activities.login.LoginActivity
import com.example.popmate.view.activities.user.MyPageLogoutActivity
import com.example.popmate.view.fragments.popupstore.HomeFragment
import com.example.popmate.view.fragments.popupstore.PopupStoreFragment
import com.example.popmate.view.fragments.user.MyPageLoginFragment
import com.example.popmate.view.fragments.user.MyPageLogoutFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Stack

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val fragmentStack: Stack<Fragment> = Stack()
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Log.d("dddddd", ApiClient.getJwtToken().toString())
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_home -> setCurrentFragment(HomeFragment(), false)
                R.id.page_popupstore -> setCurrentFragment(PopupStoreFragment(), false)
                R.id.page_mypage -> if(ApiClient.getJwtToken()==null){
                    setCurrentFragment(MyPageLoginFragment(), false)
                }else{
                    setCurrentFragment(MyPageLogoutFragment(), false)
                }
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
