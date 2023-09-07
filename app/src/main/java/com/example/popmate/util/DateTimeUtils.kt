package com.example.popmate.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class DateTimeUtils @Inject constructor() {

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    /**
     * LocalDateTime을 yyyy-MM-dd 형식의 String으로 변환
     */
    fun toDateString(date: LocalDateTime): String {
        return date.format(formatter)
    }

    /**
     * LocalDateTime을 yyyy-MM-dd HH:mm 형식의 String으로 변환
     */
    fun toDateTimeString(date: LocalDateTime): String {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
    }

    /**
     * LocalDateTime을 HH:mm 형식의 String으로 변환
     */
    fun toTimeString(date: LocalDateTime): String {
        return date.format(DateTimeFormatter.ofPattern("HH:mm"))
    }
}
