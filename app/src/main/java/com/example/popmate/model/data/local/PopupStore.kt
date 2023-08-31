package com.example.popmate.model.data.local

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PopupStore(
    val id: Int,
    val title: String,
    val openDate: Date,
    val closeDate: Date,
    val location: String,
    val organizer: String,
    val imgUrl: String,
){
    val openDateFormatted: String
        get() {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val openDateStr = dateFormat.format(openDate)
            val closeDateStr = dateFormat.format(closeDate)
            return "$openDateStr ~ $closeDateStr"
        }
}
