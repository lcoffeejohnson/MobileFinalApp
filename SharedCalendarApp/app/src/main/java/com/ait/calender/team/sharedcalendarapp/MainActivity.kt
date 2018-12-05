package com.ait.calender.team.sharedcalendarapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnTempCalendarList.setOnClickListener {
            startActivity(
                    Intent(this@MainActivity,
                            CalendarActivity::class.java)
            )
        }
    }

}
