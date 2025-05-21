package com.example.nomo.ui.addticket

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nomo.ui.common.CustomTextField
import com.example.nomo.ui.theme.DropdownBgColor
import com.example.nomo.ui.theme.SFProDisplay
import com.example.nomo.ui.theme.SecondaryTextOnHover
import com.example.nomo.ui.theme.SecondaryTextOnPrimary
import com.example.nomo.R
import com.example.nomo.ui.theme.Black

@Composable
fun DebtInputDropdown(
    initialAmount: String,
    onSave: (String) -> Unit,
    onClose: () -> Unit
) {
    var inputAmount by remember { mutableStateOf(initialAmount) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(DropdownBgColor, RoundedCornerShape(10.dp))
            .padding(vertical = 8.dp, horizontal = 4.dp)
    ) {
        CustomTextField(
            value = inputAmount,
            onValueChange = { inputAmount = it },
            placeholder = "Сумма",
            leadingIcon = R.drawable.ic_dollar
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(horizontalArrangement = Arrangement.End) {
            TextButton(onClick = {
                if (inputAmount.isNotBlank()) {
                    onSave(inputAmount)
                }
                onClose()
            }) {
                Text(
                    text = "Сохранить",
                    fontSize = 14.sp,
                    fontFamily = SFProDisplay,
                    fontWeight = FontWeight.SemiBold,
                    color = Black
                )
            }
        }
    }
}