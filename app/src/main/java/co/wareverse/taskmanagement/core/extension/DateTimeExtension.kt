package co.wareverse.taskmanagement.core.extension

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

const val API_PATTERN = "yyyy-MM-dd'T'HH:mm:ss[.SSS][XXX]"
const val d_MMMM_yyyy = "d MMMM yyyy"

fun String.toLocalDateTime(pattern: String): LocalDateTime {
    return LocalDateTime.parse(this, DateTimeFormatter.ofPattern(pattern))
}

fun LocalDateTime.toPattern(pattern: String): String {
    return this.format(DateTimeFormatter.ofPattern(pattern))
}

fun nowInMillis(zone: ZoneId = ZoneOffset.UTC): Long {
    return LocalDateTime.now()
        .atZone(zone)
        .toInstant()
        .toEpochMilli()
}