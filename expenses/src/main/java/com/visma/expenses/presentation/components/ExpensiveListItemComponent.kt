package com.visma.expenses.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.visma.domain.core.extensions.toFormattedDateTime
import com.visma.domain.features.expenses.models.Expense
import com.visma.domain.features.expenses.models.VismaCurrency

@Composable
fun ExpensiveListItemComponent(expense: Expense, onActionClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
        onClick = onActionClick
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                Icons.Default.Receipt,
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = expense.description,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                )

            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = expense.amount.toString(),
                    color = Color.Blue,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                )
                Text(
                    text = expense.date.toFormattedDateTime(),
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    color = Color.Gray
                )
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun ExpensiveListItemComponentPreview() {
    ExpensiveListItemComponent(
        expense = Expense(
            id = "Lunch",
            description = "Lunch",
            amount = 12.00,
            date = java.time.LocalDateTime.now(),
            currency = VismaCurrency.EUR
            )
    ) {}
}