package com.ait.calender.team.sharedcalendarapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.helper.ItemTouchHelper
import com.ait.calender.team.sharedcalendarapp.adapter.CalendarAdapter
import com.ait.calender.team.sharedcalendarapp.touch.EventTouchHelperCallback
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        val KEY_ITEM_TO_EDIT = "KEY_ITEM_TO_EDIT"
        val KEY_FIRST = "KEY_FIRST"
    }

    private lateinit var calendarAdapter: CalendarAdapter
    private var editIndex: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()

        fabAddCalendar.setOnClickListener {
            showAddCalendarDialog()
        }
    }

    private fun initRecyclerView() {
        Thread {
            val userId = FirebaseAuth.getInstance().currentUser!!.uid
            runOnUiThread {
                calendarAdapter = CalendarAdapter(this@MainActivity, userId)
                recyclerCalendar.adapter = calendarAdapter
                val callback = EventTouchHelperCallback(calendarAdapter)
                val touchHelper = ItemTouchHelper(callback)
                touchHelper.attachToRecyclerView(recyclerCalendar)
            }
        }.start()
    }


    private fun showAddCalendarDialog() {
        AddCalendarDialog().show(supportFragmentManager, "TAG_CREATE")
    }
}
