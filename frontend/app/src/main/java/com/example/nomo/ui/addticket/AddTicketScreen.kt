package com.example.nomo.ui.addticket

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.nomo.ui.common.BottomBar
import com.example.nomo.ui.common.TopBar


@Composable
@Preview(showBackground = true)
fun AddTicketScreen() {
    Column() {
        TopBar()
        TextAddTicket()
        AddEntryScreen(
            userId = 12312, // TODO: Handle REAL userId
            onSaveRoom = { name, desc, users -> },
            onSaveEntry = { name, desc, debts -> }
        )
        BottomBar()
    }
}