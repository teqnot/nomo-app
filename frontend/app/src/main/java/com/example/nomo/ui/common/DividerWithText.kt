package com.example.nomo.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nomo.ui.theme.SecondaryTextOnBackground

@Composable
fun DividerWithText() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = SecondaryTextOnBackground,
            thickness = 2.dp
        )
        Text(
            text = "или",
            modifier = Modifier.padding(horizontal = 8.dp),
            style = MaterialTheme.typography.bodySmall,
            color = SecondaryTextOnBackground
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = SecondaryTextOnBackground,
            thickness = 2.dp
        )
    }
}