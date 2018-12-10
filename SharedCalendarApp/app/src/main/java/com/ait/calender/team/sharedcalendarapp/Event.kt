package com.ait.calender.team.sharedcalendarapp

data class Event(var uid: String = "",
                var author: String = "",
                var title: String = "",
                var day: String = "",
                var month: String = "",
                var year: String = "",
                var hour: String = "",
                var minute: String = "",
                var description: String = "",
                var allDay: Boolean = false)