package com.ait.calender.team.sharedcalendarapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_calendar.*


class CalendarActivity : AppCompatActivity() {

    companion object {
        val KEY_EDIT_DATA = "KEY_EDIT_DATA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        fabAddEventFromMonth.setOnClickListener {
            showAddEventDialog()
        }

//        btnWeekMonth.setOnClickListener {
//            startActivity(
//                    Intent(this@CalendarActivity,
//                    WeekActivity::class.java))
//        }

        cvMain.setOnDateChangedListener { cvMain, date, selected ->
            var intentStart = Intent()
            intentStart.setClass(this@CalendarActivity, MainActivity::class.java)
            intentStart.putExtra(KEY_EDIT_DATA, cvMain.selectedDate)
            startActivity(intentStart)
        }
    }

    private fun showAddEventDialog() {
        AddEventDialog().show(supportFragmentManager, "TAG_CREATE")
    }
}
