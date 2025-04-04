package com.visma.expenses.presentation.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerComponent(
    selectedDateTime: LocalDateTime?,
    onDateSelected: (LocalDateTime) -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDateTime?.toInstant(ZoneOffset.UTC)?.toEpochMilli() ?: 0
    )
    val timePickerState =
        rememberTimePickerState(
            is24Hour = true,
            initialHour = selectedDateTime?.hour ?: 0,
            initialMinute = selectedDateTime?.minute ?: 0,
        )

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    val selectedDate = selectedDateTime?.toLocalDate()
    val selectedTime = selectedDateTime?.toLocalTime()

    var formattedDate by remember { mutableStateOf("") }
    var formattedTime by remember { mutableStateOf("") }

    formattedDate = formatDate(selectedDate)
    formattedTime = formatTime(selectedTime)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = formattedDate,
            onValueChange = {},
            label = { Text("Select Date") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Pick Date")
                }
            }
        )


        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = formattedTime,
            onValueChange = {},
            label = { Text("Select Time") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showTimePicker = true }) {
                    Icon(Icons.Default.AccessTime, contentDescription = "Pick Time")
                }
            }
        )
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val millis = datePickerState.selectedDateMillis
                    millis?.let {
                        val localDate = Instant.ofEpochMilli(it)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        val updatedDateTime =
                            LocalDateTime.of(localDate, selectedTime ?: LocalTime.MIDNIGHT)

                        formattedDate = formatDate(updatedDateTime.toLocalDate())
                        onDateSelected(updatedDateTime)

                    }
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }


    if (showTimePicker) {
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val newTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
                    val updatedDateTime = LocalDateTime.of(selectedDate ?: LocalDate.now(), newTime)
                    formattedTime = formatTime(updatedDateTime.toLocalTime())
                    onDateSelected(updatedDateTime)
                    showTimePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) {
                    Text("Cancel")
                }
            },
            text = {
                TimePicker(state = timePickerState)
            }
        )
    }

}


private fun formatDate(input: LocalDate?): String {
    return input?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) ?: "Select Date"
}

private fun formatTime(input: LocalTime?): String {
    return input?.format(DateTimeFormatter.ofPattern("HH:mm")) ?: "Select Time"
}