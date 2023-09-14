package com.example.popmate.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class DateTimeUtils @Inject constructor() {

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    /**
     * String을 yyyy-MM-dd 형식의 String으로 변환
     */
    fun toDateString(date: String): String {
        return date.toLocalDateTime().format(formatter)
    }

    /**
     * String을 yyyy-MM-dd HH:mm 형식의 String으로 변환
     */
    fun toDateTimeString(date: String): String {
        return date.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
    }

    /**
     * String을 HH:mm 형식의 String으로 변환
     */
    fun toTimeString(date: String): String {
        return date.toLocalDateTime().format(DateTimeFormatter.ofPattern("HH:mm"))
    }

    /**
     * String에서 HH시 mm분으로 변환
     */
    fun toHourMinuteString(date: String): String {
        return date.toLocalDateTime().format(DateTimeFormatter.ofPattern("HH시 mm분"))
    }

    /**
     * String을 MM.dd (D) HH:mm 형식의 String으로 변환
     */
    fun toMonthDayTimeString(date: String): String {
        return date.toLocalDateTime().format(DateTimeFormatter.ofPattern("MM.dd (E) HH:mm"))
    }

    private fun String.toLocalDateTime(): LocalDateTime {
        return LocalDateTime.parse(this)
    }
}
