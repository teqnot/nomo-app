package com.example.nomo.ui.firstlogin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nomo.ui.theme.PrimaryColor
import com.example.nomo.ui.theme.SFProDisplay
import com.example.nomo.ui.theme.White

@Composable
fun BottomButtons(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally // Центрирование кнопок по горизонтали
    ) {
        // Кнопка "Войти"
        Button(
            onClick = onLoginClick, // Use the passed callback
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Войти",
                fontFamily = SFProDisplay,
                fontWeight = FontWeight.Bold,
                color = White,
                fontSize = 24.sp,
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp)) // Отступ между кнопками

        OutlinedButton(
            onClick = onRegisterClick, // Use the passed callback
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(width = 2.dp, color = PrimaryColor), // Border-Only кнопка
            colors = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryColor),
            modifier = Modifier.fillMaxWidth() // Кнопка занимает всю ширину
        ) {
            Text(
                text = "Зарегестрироваться",
                fontFamily = SFProDisplay,
                fontWeight = FontWeight.Bold,
                color = PrimaryColor,
                fontSize = 24.sp,
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }
    }
}
