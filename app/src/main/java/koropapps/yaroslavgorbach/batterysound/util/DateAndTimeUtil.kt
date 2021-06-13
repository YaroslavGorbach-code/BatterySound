package koropapps.yaroslavgorbach.batterysound.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun convertHourAndMinuteToString(h: Int, m: Int, is24HourFormat: Boolean): String {
    val calendar = Calendar.getInstance()
    calendar[Calendar.HOUR_OF_DAY] = h
    calendar[Calendar.MINUTE] = m
    val date = calendar.time

    if (!is24HourFormat){
        val dateFormat: DateFormat = SimpleDateFormat("hh:mm aa", Locale.getDefault())
        return dateFormat.format(date)
    }
    var stringH = "$h"
    var stringM = "$m"
    if (stringH.length < 2) stringH = "0$stringH"
    if (stringM.length < 2) stringM = "0$stringM"
    return "$stringH:$stringM"

}