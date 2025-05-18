package com.example.nomo.ui.mainpage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nomo.ui.filters.DebtFilter
import com.example.nomo.ui.theme.YouAreOwedColor
import com.example.nomo.ui.theme.YouOweColor
import com.example.nomo.ui.theme.AccentColorYouOwe
import com.example.nomo.ui.theme.AccentColorYouOwed
import com.example.nomo.ui.theme.SFProDisplay
import com.example.nomo.ui.theme.SFProText
import com.example.nomo.ui.theme.SecondaryTextOnBackground
import com.example.nomo.ui.theme.SecondaryTextOnPrimary

@Composable
fun BalanceSection(
    oweAmount: String,
    owedAmount: String,
    currentFilter: DebtFilter,
    onCardClick: (DebtFilter) -> Unit) {
    val totalCharacters = oweAmount.length + owedAmount.length
    val isVertical = totalCharacters > 16 // Определяем, нужно ли вертикальное выравнивание

    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
        Text(
            text = "Ваш баланс:",
            color = SecondaryTextOnBackground,
            fontSize = 14.sp,
            fontFamily = SFProText,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )

        if (isVertical) {
            // Вертикальное отображение
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp) // Отступ между карточками
            ) {
                BalanceCard(
                    title = "Вы должны",
                    amount = oweAmount,
                    bgColor = YouOweColor,
                    lineColor = AccentColorYouOwe,
                    isVertical = isVertical,
                    isSelected = currentFilter == DebtFilter.YOU_OWE,
                    onClick = { onCardClick(DebtFilter.YOU_OWE) }
                )
                BalanceCard(
                    title = "Вам должны",
                    amount = owedAmount,
                    bgColor = YouAreOwedColor,
                    lineColor = AccentColorYouOwed,
                    isVertical= isVertical,
                    isSelected = currentFilter == DebtFilter.YOU_ARE_OWED,
                    onClick = { onCardClick(DebtFilter.YOU_ARE_OWED) }
                )
            }
        } else {
            // Горизонтальное отображение
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BalanceCard(
                    title = "Вы должны",
                    amount = oweAmount,
                    bgColor = YouOweColor,
                    lineColor = AccentColorYouOwe,
                    isVertical = isVertical,
                    isSelected = currentFilter == DebtFilter.YOU_OWE,
                    onClick = { onCardClick(DebtFilter.YOU_OWE) }
                )
                BalanceCard(
                    title = "Вам должны",
                    amount = owedAmount,
                    bgColor = YouAreOwedColor,
                    lineColor = AccentColorYouOwed,
                    isVertical= isVertical,
                    isSelected = currentFilter == DebtFilter.YOU_ARE_OWED,
                    onClick = { onCardClick(DebtFilter.YOU_ARE_OWED) }
                )
            }
        }
    }
}

@Composable
fun BalanceCard(
    title: String,
    amount: String,
    bgColor: Color,
    lineColor: Color,
    isVertical: Boolean = false,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val cardBgColor = if (isSelected) bgColor else bgColor.copy(alpha = 0.7f)

    Box(
        modifier = Modifier
            .background(cardBgColor, RoundedCornerShape(24.dp))
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .then(
                    if (isVertical) Modifier.fillMaxWidth() // В вертикальном режиме занимаем всю ширину
                    else Modifier // В горизонтальном режиме оставляем дефолтный вид
                ),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontFamily = SFProDisplay,
                fontWeight = FontWeight.SemiBold,
                color = SecondaryTextOnPrimary
            )
            Box(
                modifier = Modifier
                    .height(2.dp)
                    .background(lineColor)
                    .then(
                        if (isVertical) Modifier.fillMaxWidth() // В вертикальном режиме линия на всю ширину
                        else Modifier.width(142.dp) // В горизонтальном режиме фиксированная ширина
                    )
            )
            Text(
                text = amount,
                fontSize = 32.sp,
                fontFamily = SFProDisplay,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )
        }
    }
}