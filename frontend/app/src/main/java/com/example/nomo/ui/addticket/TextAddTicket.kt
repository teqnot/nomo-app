package com.example.nomo.ui.addticket

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nomo.R
import com.example.nomo.ui.theme.DividerColorMainBalance
import com.example.nomo.ui.theme.SFProDisplay

@Composable
@Preview(showBackground = true)
fun TextAddTicket() {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Создать новую запись",
                fontSize = 20.sp,
                fontFamily = SFProDisplay,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.width(15.dp))
        HorizontalDivider(
            thickness = 2.dp,
            color = DividerColorMainBalance,
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        )
    }
}