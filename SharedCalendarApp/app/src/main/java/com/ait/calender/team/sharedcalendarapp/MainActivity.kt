package com.ait.calender.team.sharedcalendarapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import com.ait.calender.team.sharedcalendarapp.adapter.CalendarAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AddCalendarDialog.CalendarHandler {

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

        btnTempCalendarList.setOnClickListener {
            startActivity(
                    Intent(this@MainActivity,
                            CalendarActivity::class.java)
            )
        }
    }

    private fun initRecyclerView() {
        Thread {
            val calendars = null // get calendars from Firebase
            runOnUiThread {
                calendarAdapter = CalendarAdapter(this@MainActivity, calendars)
                recyclerCalendar.adapter = calendarAdapter
                val callback = EventTouchHelperCallback(calendarAdapter)
                val touchHelper = ItemTouchHelper(callback)
                touchHelper.attachToRecyclerView(recyclerCalendar)
            }
        }.start()
    }


    private fun showAddCalendarDialog() {
        AddCalendarDialog().show(supportFragmentManager, getString(R.string.tag_create))
    }



    override fun calenderCreated(item: Calendar) {

        Thread {
            val id = AppDatabase.getInstance(
                    this@MaingActivity).productDao().insertProduct(item)

            item.calendarId = id

            runOnUiThread {
                calendarAdapter.addProduct(item)
            }
        }.start()

    }



}
