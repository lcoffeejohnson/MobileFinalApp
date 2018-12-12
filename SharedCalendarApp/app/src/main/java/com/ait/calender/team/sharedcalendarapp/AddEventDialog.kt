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

class AddEventDialog : DialogFragment(), AdapterView.OnItemSelectedListener {

    val hours : Array<String> = Array(24) { "" }
    val minutes: Array<String> = Array(60) { "" }

    private lateinit var eventTitle : EditText
    private lateinit var eventDate : DatePicker
    private lateinit var eventHour : Spinner
    private lateinit var eventMinute : Spinner
    private lateinit var eventAllDay : CheckBox
    private lateinit var eventDesc : EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        for (i in 0..23) {
            if (i < 10) {
                hours[i] = "0" + i.toString()
            } else {
                hours[i] = i.toString()
            }

        }

        for (i in 0..59) {
            if (i < 10) {
                minutes[i] = "0" + i.toString()
            } else {
                minutes[i] = i.toString()
            }
        }

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Add New Event")

        val rootView = requireActivity().layoutInflater.inflate(
                R.layout.dialog_new_event, null
        )

        eventTitle = rootView.etEventTitle
        eventDate = rootView.dpEventDate
        eventHour = rootView.hourSelect
        eventMinute = rootView.minuteSelect
        eventHour.onItemSelectedListener = this
        eventMinute.onItemSelectedListener = this
        eventAllDay = rootView.cbAllDay
        eventDesc = rootView.etEventDesc

        val hoursSpinner = ArrayAdapter(activity, android.R.layout.simple_spinner_item, hours)
        hoursSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        eventHour.adapter = hoursSpinner

        val minutesSpinner = ArrayAdapter(activity, android.R.layout.simple_spinner_item, minutes)
        minutesSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        eventMinute.adapter = minutesSpinner

        builder.setPositiveButton("Add") {
            dialog, which -> uploadEvent()
        }

        builder.setNegativeButton("Cancel") {
            dialog, which ->
        }


        eventDate.updateDate(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR),
                java.util.Calendar.getInstance().get(java.util.Calendar.MONTH),
                java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH))

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
                eventTitle.text.isEmpty() -> eventTitle.error = "Event must have a title"
                else -> {
                    uploadEvent()
                    dialog.dismiss()
                }
            }
        }
    }

    private fun uploadEvent() {
        val event = Event(
                FirebaseAuth.getInstance().currentUser!!.uid,
                FirebaseAuth.getInstance().currentUser!!.displayName!!,
                eventTitle.text.toString(),
                eventDate.dayOfMonth.toString(),
                eventDate.month.toString(),
                eventDate.year.toString(),
                eventHour.selectedItem.toString(),
                eventMinute.selectedItem.toString(),
                eventDesc.text.toString(),
                eventAllDay.isChecked
        )

        val eventCollections = FirebaseFirestore.getInstance().collection("events")

        eventCollections.add(event).addOnSuccessListener {
            Toast.makeText(context, "Event saved",
                    Toast.LENGTH_LONG).show()
        }.addOnFailureListener{
            Toast.makeText(context, "Error ${it.message}",
                    Toast.LENGTH_LONG).show()
        }
    }
}