package com.example.nomo.ui.mainpage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nomo.R
import com.example.nomo.ui.theme.DividerColorBalances
import com.example.nomo.ui.theme.SFProDisplay
import com.example.nomo.ui.theme.SFProText
import com.example.nomo.ui.theme.YouOweColor
import com.example.nomo.ui.theme.YouAreOwedColor
import androidx.compose.runtime.*

data class Debt(
    val name: String,
    val amount: String,
    val isOwedToMe: Boolean,
    var isOpened: Boolean = false
)

@Composable
fun DebtList(debts: List<Debt>, modifier: Modifier = Modifier) {
    val debtListState = remember {
        debts.map { it.copy() }.toMutableStateList()
    }

    // Динамическое обновление "долгов"
    // В первую очередь - для корректной фильтрации
    // Затем для правильной логики подгрузки api
    LaunchedEffect(debts) {
        debtListState.clear()
        debtListState.addAll(debts.map { it.copy() })
    }

    LazyColumn(modifier = modifier
        .fillMaxWidth()
        .padding(20.dp)
    ) {
        items(debtListState, key = { it.name }) { debt ->
            Spacer(modifier = Modifier.height(8.dp))
            ExpandableDebtItem(debt = debt) { // Выпадающее поле ввода суммы
                val index = debtListState.indexOfFirst { it.name == debt.name }
                if (index != -1) {
                    debtListState[index] = debt.copy(isOpened = !debt.isOpened)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(thickness = 2.dp, color = DividerColorBalances)
        }
    }
}

@Composable
fun ExpandableDebtItem(debt: Debt, onToggle: () -> Unit) {
    var inputAmount by remember { mutableStateOf("") }
    var isDropdownOpen by remember { mutableStateOf(debt.isOpened) }

    LaunchedEffect(debt.isOpened) {
        isDropdownOpen = debt.isOpened
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onToggle() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Левая часть: аватар и имя
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_avatar),
                    contentDescription = "Avatar",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = debt.name,
                    fontSize = 16.sp,
                    fontFamily = SFProDisplay,
                    fontWeight = FontWeight.Bold
                )
            }

            // Правая часть: сумма и стрелка
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            if (debt.isOwedToMe) YouAreOwedColor else YouOweColor,
                            RoundedCornerShape(5.dp)
                        )
                        .padding(4.dp)
                ) {
                    Text(
                        text = debt.amount,
                        fontFamily = SFProText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_nav_forward),
                    contentDescription = "Arrow"
                )
            }
        }

        if (debt.isOpened) {
            Spacer(modifier = Modifier.height(11.dp))
            TicketDropdown(
                initialAmount = debt.amount,
                onAmountChange = { newAmount ->
                    inputAmount = newAmount
                },
                onSave = {
                    // TODO: Логика колла к апи - "вычитание" из долга
                },
                onClose = {
                    onToggle()
                }
            )
        }
    }
}