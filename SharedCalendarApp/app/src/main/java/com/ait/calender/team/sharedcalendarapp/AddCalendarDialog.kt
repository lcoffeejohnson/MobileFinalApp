package com.ait.calender.team.sharedcalendarapp

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.dialog_new_event.*
import kotlinx.android.synthetic.main.dialog_new_event.view.*

class AddCalendarDialog : DialogFragment(), AdapterView.OnItemSelectedListener {

    interface CalendarHandler {
        fun calendarCreated(item: Calendar)
    }

    private lateinit var calendarHandler: CalendarHandler

    private lateinit var calendarTitle: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Add Calendar")

        val rootView = requireActivity().layoutInflater.inflate(
                R.layout.dialog_new_calendar, null
        )

        calendarTitle = rootView.etCalendarTitle


        builder.setPositiveButton("Add") { dialog, which ->
            uploadEvent()
        }

        builder.setNegativeButton("Cancel") { dialog, which ->
        }

        builder.setView(rootView)
        return builder.create()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }

    override fun onResume() {
        super.onResume()
        val positiveButton = (dialog as AlertDialog).getButton(Dialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {
            when {
                calendarTitle.text.isEmpty() -> calendarTitle.error = "Calendar must have a title"
                else -> {
                    uploadEvent()
                }
            }
        }
    }

    private fun uploadEvent() {
        val calendar = Calendar(
                FirebaseAuth.getInstance().currentUser!!.uid,
                calendarTitle.text.toString(),
                null
        )

        val calendarCollections = FirebaseFirestore.getInstance().collection("calendars")

        calendarCollections.add(calendar)
    }
}