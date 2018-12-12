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
import com.ait.calender.team.sharedcalendarapp.Event
import com.ait.calender.team.sharedcalendarapp.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.row_event.view.*


class EventAdapter(var context: Context, var uid:String) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    private var eventsList = mutableListOf<Event>()
    private var eventKeys = mutableListOf<String>()
    private var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
                R.layout.row_event, parent, false
        )
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return eventsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = eventsList.get(holder.adapterPosition)

        holder.tvAuthor.text = event.author
        holder.tvTitle.text = event.title
        holder.tvDescription.text = event.description
        holder.tvTime.text = "Time: " + event.hour + ":" + event.minute
        holder.tvDescription.text = "Date: " + event.day + "/" + event.month + "/" + event.year

        if (event.uid == uid) {
            holder.btnDelete.visibility = View.VISIBLE
            holder.btnDelete.setOnClickListener {
                removePost(holder.adapterPosition)
            }
        } else {
            holder.btnDelete.visibility = View.GONE
        }

        setAnimation(holder.itemView, position)
    }

    fun addEvent(event: Event, key: String) {
        eventsList.add(event)
        eventKeys.add(key)
        notifyDataSetChanged()
    }

    private fun removePost(index: Int) {
        FirebaseFirestore.getInstance().collection("posts").document(
                eventKeys[index]
        ).delete()

        eventsList.removeAt(index)
        eventKeys.removeAt(index)
        notifyItemRemoved(index)
    }


    fun removePostByKey(key: String) {
        val index = eventKeys.indexOf(key)
        if (index != -1) {
            eventsList.removeAt(index)
            eventKeys.removeAt(index)
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


        val tvTitle: TextView = itemView.tvEventTitle
        val tvAuthor: TextView = itemView.tvAuthor
        val tvDescription: TextView = itemView.tvDescription
        val tvTime: TextView = itemView.tvTime
        val tvDate: TextView = itemView.tvDate
        val btnDelete: Button = itemView.btnDelete

    }

}