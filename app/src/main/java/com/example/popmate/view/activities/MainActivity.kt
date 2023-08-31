package com.example.popmate.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.popmate.R
import com.example.popmate.view.fragments.HomeFragment
import com.example.popmate.view.fragments.MyPageFragment
import com.example.popmate.view.fragments.PopupStoreFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val firstFragment=HomeFragment()
        val secondFragment=PopupStoreFragment()
        val thirdFragment=MyPageFragment()

        setCurrentFragment(firstFragment)

        var bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.page_home->setCurrentFragment(firstFragment)
                R.id.page_popupstore->setCurrentFragment(secondFragment)
                R.id.page_mypage->setCurrentFragment(thirdFragment)

            }
            true
        }
    }


 }
