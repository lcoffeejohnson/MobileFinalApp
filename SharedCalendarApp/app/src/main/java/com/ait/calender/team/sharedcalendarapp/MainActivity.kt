package com.ait.calender.team.sharedcalendarapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.widget.Toast
import com.ait.calender.team.sharedcalendarapp.touch.EventTouchHelperCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //private lateinit var calendarAdapter: CalendarAdapter
    private var editIndex: Int = 0

    private lateinit var eventAdapter: EventAdapter
    private lateinit var eventListener: ListenerRegistration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fabAddCalendar.setOnClickListener { view ->
            //show event dialog
        }


        eventAdapter = EventAdapter(this,
                FirebaseAuth.getInstance().currentUser!!.uid)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        recyclerEvents.layoutManager = layoutManager
        recyclerEvents.adapter = eventAdapter

        initPosts()
    }

    fun initPosts() {
        val db = FirebaseFirestore.getInstance()
        val postsCollection = db.collection("posts")

        eventListener = postsCollection.addSnapshotListener(object: EventListener<QuerySnapshot> {
            override fun onEvent(querySnapshot: QuerySnapshot?, p1: FirebaseFirestoreException?) {
                if (p1 != null) {
                    Toast.makeText(this@MainActivity, "Error: ${p1.message}",
                            Toast.LENGTH_LONG).show()
                    return
                }

                for (docChange in querySnapshot!!.getDocumentChanges()) {
                    when (docChange.type) {
                        DocumentChange.Type.ADDED -> {
                            val post = docChange.document.toObject(Event::class.java)
                            eventAdapter.addPost(post, docChange.document.id)
                        }
                        DocumentChange.Type.MODIFIED -> {

                        }
                        DocumentChange.Type.REMOVED -> {
                            eventAdapter.removePostByKey(docChange.document.id)

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

}
