package com.ait.calender.team.sharedcalendarapp

data class Calendar(var uid: String = "",
                    var title: String = "",
                    var events: List<Event>?)