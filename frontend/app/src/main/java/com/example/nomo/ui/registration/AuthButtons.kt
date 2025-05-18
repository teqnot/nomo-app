package com.example.nomo.ui.registration

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nomo.ui.common.DividerWithText
import com.example.nomo.ui.theme.*

@Composable
fun AuthButtons(
    onRegister: () -> Unit,
    onLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Кнопка "Зарегистрироваться"
        Button(
            onClick = onRegister,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Зарегистрироваться",
                fontFamily = SFProDisplay,
                fontWeight = FontWeight.Bold,
                color = White,
                fontSize = 24.sp,
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }

        Spacer(modifier = Modifier.height(11.dp))

        // Разделитель с текстом "или"
        DividerWithText()

        Spacer(modifier = Modifier.height(11.dp))

        // Кнопка "Войти"
        OutlinedButton(
            onClick = onLogin,
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(width = 2.dp, color = PrimaryColor), // Border-Only кнопка
            colors = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryColor),
            modifier = Modifier.fillMaxWidth() // Кнопка занимает всю ширину
        ) {
            Text(
                text = "Войти",
                fontFamily = SFProDisplay,
                fontWeight = FontWeight.Bold,
                color = PrimaryColor,
                fontSize = 24.sp,
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }
    }
}