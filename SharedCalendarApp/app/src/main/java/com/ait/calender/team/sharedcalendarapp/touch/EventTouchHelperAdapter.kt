package com.ait.calender.team.sharedcalendarapp.touch

import java.text.FieldPosition

interface EventTouchHelperAdapter {
    fun onDismissed(position: Int)
    fun onItemMoved(fromPosition: Int, toPosition: Int)
}