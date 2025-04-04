package com.visma.expenses.presentation.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.UnfoldMore
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DropdownSelector(
    title: String,
    selected: String,
    options: List<String>,
    onSelectionChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth().clickable {
        expanded = true
    }) {
            OutlinedTextField(
                value = selected,
                onValueChange = {},
                label = { Text(if(selected.isEmpty()) "" else title, style = MaterialTheme.typography.labelSmall) },
                readOnly = true,
                enabled = false,
                trailingIcon = {
                    Icon(Icons.Default.UnfoldMore, contentDescription = "Pick option")
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.Gray,
                    disabledBorderColor = Color.Gray,
                    unfocusedTextColor = Color.Black,
                    focusedTextColor = Color.Black,
                    disabledTextColor = Color.Black,
                    disabledTrailingIconColor = Color.Black
                )
            )


        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(text = { Text(option) }, onClick = {
                    onSelectionChange(option)
                    expanded = false
                })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DropdownSelectorPreview() {
    DropdownSelector("Currency", "EUR", listOf("EUR", "USD", "JPY", "OTHER"), {})

}