package com.ait.calender.team.sharedcalendarapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.ait.calender.team.sharedcalendarapp.adapter.EventAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var editIndex: Int = 0

    private lateinit var eventAdapter: EventAdapter
    private lateinit var eventListener: ListenerRegistration
    private lateinit var dateSelected: CalendarDay

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (intent.hasExtra(CalendarActivity.KEY_EDIT_DATA)) {
            dateSelected = intent.getParcelableExtra(CalendarActivity.KEY_EDIT_DATA)
        }

        fabAddEventOnDay.setOnClickListener { view ->
            showAddEventDialog()
        }

        eventAdapter = EventAdapter(this,
                FirebaseAuth.getInstance().currentUser!!.uid)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        recyclerEvents.layoutManager = layoutManager
        recyclerEvents.adapter = eventAdapter

        initEvents()
    }

    fun initEvents() {
        val db = FirebaseFirestore.getInstance()
        val postsCollection = db.collection("events")

        eventListener = postsCollection.addSnapshotListener(object: EventListener<QuerySnapshot> {
            override fun onEvent(querySnapshot: QuerySnapshot?, p1: FirebaseFirestoreException?) {
                if (p1 != null) {
                    Toast.makeText(this@MainActivity, (getString(R.string.error) + p1.message),
                            Toast.LENGTH_LONG).show()
                    return
                }

                for (docChange in querySnapshot!!.getDocumentChanges()) {
                    when (docChange.type) {
                        DocumentChange.Type.ADDED -> {
                            val event = docChange.document.toObject(Event::class.java)
                            eventAdapter.addEvent(event, docChange.document.id)
                        }
                        DocumentChange.Type.MODIFIED -> {

                        }
                        DocumentChange.Type.REMOVED -> {
                            eventAdapter.removeEventByKey(docChange.document.id)
                        }
                    }
                }

            }
        })
    }

    override fun onDestroy() {
        eventListener.remove()
        super.onDestroy()
    }

    private fun showAddEventDialog() {
        AddEventDialog().show(supportFragmentManager, "TAG_CREATE")
    }
}
