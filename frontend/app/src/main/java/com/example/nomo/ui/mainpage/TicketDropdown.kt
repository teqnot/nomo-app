package com.example.nomo.ui.mainpage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nomo.R
import com.example.nomo.ui.theme.DropdownBgColor
import com.example.nomo.ui.theme.SFProDisplay
import com.example.nomo.ui.theme.SecondaryTextOnHover
import com.example.nomo.ui.common.CustomTextField
import androidx.compose.runtime.*

@Composable
fun TicketDropdown(
    initialAmount: String,
    onAmountChange: (String) -> Unit,
    onSave: () -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    var inputAmount by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(DropdownBgColor, RoundedCornerShape(10.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Счет",
            fontSize = 16.sp,
            fontFamily = SFProDisplay,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(color = SecondaryTextOnHover, thickness = 2.dp)
        Spacer(modifier = Modifier.height(12.dp))

        CustomTextField(
            value = inputAmount,
            onValueChange = { newText ->
                inputAmount = newText
                onAmountChange(newText)
            },
            placeholder = "Сумма",
            leadingIcon = R.drawable.ic_dollar
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            TextButton(onClick = {
                inputAmount = initialAmount
            }) {
                Text(
                    text = "Ввести всю сумму",
                    fontSize = 14.sp,
                    fontFamily = SFProDisplay,
                    fontWeight = FontWeight.SemiBold,
                    color = SecondaryTextOnHover
                )
            }
            TextButton(onClick = {
                if (inputAmount.isNotBlank()) {
                    onSave()
                    onClose() // Закрываем dropdown
                }
            }) {
                Text(
                    text = "Сохранить",
                    fontSize = 14.sp,
                    fontFamily = SFProDisplay,
                    fontWeight = FontWeight.SemiBold,
                    color = SecondaryTextOnHover
                )
            }
        }
    }
}