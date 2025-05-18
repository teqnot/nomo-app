package com.example.nomo.ui.mainpage

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nomo.ui.common.BottomBar
import com.example.nomo.ui.common.TopBar
import com.example.nomo.ui.filters.DebtFilter

@Composable
@Preview(showBackground = true)
fun MainScreen() {
    var debtFilter by remember { mutableStateOf(DebtFilter.YOU_ARE_OWED) }

    var allDebts = remember {
        mutableStateListOf(
            Debt("Иван Борисов", "331₽", false),
            Debt("Илья Макаров", "8912₽", true),
            Debt("Артем Рожков", "87 091₽", true)
        )
    }

    val filteredDebts = remember(debtFilter, allDebts) {
        derivedStateOf {
            when (debtFilter) {
                DebtFilter.YOU_ARE_OWED -> allDebts.filter { it.isOwedToMe }
                DebtFilter.YOU_OWE -> allDebts.filter { !it.isOwedToMe }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars),
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TopBar()
            GreetingUser("samplename")
            BalanceSection(
                oweAmount = "756,00₽",
                owedAmount = "1340,00₽",
                currentFilter = debtFilter,
                onCardClick = { newFilter ->
                    debtFilter = newFilter
                }
            )

            DebtList(debts = filteredDebts.value, modifier = Modifier.weight(1f))

            Spacer(modifier = Modifier.height(8.dp))
            BottomBar()
        }
    }
}