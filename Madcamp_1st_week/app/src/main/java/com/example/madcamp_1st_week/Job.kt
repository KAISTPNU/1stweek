package com.example.madcamp_1st_week

enum class Job {
    DEVELOPER, DESIGNER, PM, ENGINEER, ETC;

    fun convert2Str() {
        when(this){
            DEVELOPER -> "Developer"
            DESIGNER -> "Designer"
            PM -> "PM"
            ENGINEER -> "Engineer"
            ETC -> "ETC"
        }
    }
}

fun convert2Job(strjob: String) {
    if (strjob.uppercase().equals("DEVELOPER")) Job.DEVELOPER
    else if (strjob.uppercase().equals("DESIGNER")) Job.DESIGNER
    else if (strjob.uppercase().equals("PM")) Job.PM
    else if (strjob.uppercase().equals("ENGINEER")) Job.ENGINEER
    else Job.ETC
}