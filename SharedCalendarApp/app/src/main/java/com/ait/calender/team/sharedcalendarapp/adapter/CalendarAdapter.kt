package com.ait.calender.team.sharedcalendarapp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.ait.calender.team.sharedcalendarapp.Calendar
import com.ait.calender.team.sharedcalendarapp.R
import com.ait.calender.team.sharedcalendarapp.touch.EventTouchHelperAdapter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.row_calendar.view.*

class CalendarAdapter(var context: Context, var uid:String) : RecyclerView.Adapter<CalendarAdapter.ViewHolder>(), EventTouchHelperAdapter {


    private var calendarList = mutableListOf<Calendar>()
    private var calendarKeys = mutableListOf<String>()
    private var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
                R.layout.row_calendar, parent, false
        )
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return calendarList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val calendar = calendarList.get(holder.adapterPosition)

        holder.tvCalendar.text = calendar.title


        if (calendar.uid == uid) {
            holder.btnDelete.visibility = View.VISIBLE
            holder.btnDelete.setOnClickListener {
                removeCalendar(holder.adapterPosition)
            }
        } else {
            holder.btnDelete.visibility = View.GONE
        }

        setAnimation(holder.itemView, position)
    }

    fun addCalendar(calendar: Calendar, key: String) {
        calendarList.add(calendar)
        calendarKeys.add(key)
        notifyDataSetChanged()
    }

    private fun removeCalendar(index: Int) {
        FirebaseFirestore.getInstance().collection("calendars").document(
                calendarKeys[index]
        ).delete()

        calendarList.removeAt(index)
        calendarKeys.removeAt(index)
        notifyItemRemoved(index)
    }


    fun removeCalendarByKey(key: String) {
        val index = calendarKeys.indexOf(key)
        if (index != -1) {
            calendarList.removeAt(index)
            calendarKeys.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(context,
                    android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    class ViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {

        val tvCalendar: TextView = itemView.tvCalendar
        val btnDelete: Button = itemView.btnDelete

    }

    override fun onDismissed(position: Int) {
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
    }

}