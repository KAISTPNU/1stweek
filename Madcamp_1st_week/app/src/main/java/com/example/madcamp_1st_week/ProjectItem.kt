package com.example.madcamp_1st_week

import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*

data class ProjectItem (var title: String,
                        var language: String,
                        var leader: String,
                        var status: Int,
                        var start_date: LocalDate,
                        var end_date: LocalDate,) {
    var d_day: Int = ChronoUnit.DAYS.between(LocalDate.now(), end_date).toInt()
}