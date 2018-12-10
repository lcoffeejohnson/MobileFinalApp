package com.ait.calender.team.sharedcalendarapp

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_calendar.*

class CalendarActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_month -> {

                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_week -> {

                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_day -> {

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        cvMain.setDate(System.currentTimeMillis(),false,true)
    }
}
