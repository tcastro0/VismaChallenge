package com.visma.domain.core.extensions

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.toFormattedDate(): String {
    return this.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
}
fun LocalDateTime.toFormattedDateTime(): String {
    return this.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
}


fun LocalDate.toFormattedDate(): String {
    return this.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
}

fun LocalTime.toFormattedTime(): String {
    return this.format(DateTimeFormatter.ofPattern("HH:mm"))
}