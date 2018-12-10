package com.ait.calender.team.sharedcalendarapp

data class Calendar(var title: String = "",
                 var contributers: List<String>?,
                 var events: List<Event>?)