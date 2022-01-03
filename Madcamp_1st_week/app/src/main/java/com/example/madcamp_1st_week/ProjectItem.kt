package com.example.madcamp_1st_week

import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*

/*
    프로젝트 아이템의 data class
    LocalDate는 날짜 타입의 클래스입니다
 */
data class ProjectItem (var title: String,
                        var language: String,
                        var leader: String,
                        var status: Int,
                        var start_date: LocalDate,
                        var end_date: LocalDate,
                        var participants: String,
                        var todo : List<String>,
                        var email: String,
                        var phone: String,
) {
    var d_day: Int = ChronoUnit.DAYS.between(LocalDate.now(), end_date).toInt()
}