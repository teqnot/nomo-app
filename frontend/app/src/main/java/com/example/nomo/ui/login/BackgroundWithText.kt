package com.example.nomo.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nomo.R
import com.example.nomo.ui.theme.Black
import com.example.nomo.ui.theme.SFProDisplay
import com.example.nomo.ui.theme.SFProText
import com.example.nomo.ui.theme.SecondaryTextOnPrimary

@Composable
@Preview(showBackground = true)
fun BackgroundWithText() {
    Box(modifier = Modifier.fillMaxWidth().padding(top = 10.dp)) {
        // Фон
        Image(
            painter = painterResource(id = R.drawable.ic_background_login),
            contentDescription = "Background",
            modifier = Modifier.align(Alignment.TopStart).padding(top = 50.dp)
        )

        WelcomeTextRegister(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = -35.dp) // Точная настройка позиции
                .fillMaxWidth(1f)
        )

    }
}

@Composable
fun WelcomeTextRegister(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Text(
            text = "C возвращением!",
            fontFamily = SFProDisplay,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Black
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Пожалуйста войдите, чтобы продолжить",
            fontFamily = SFProText,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = SecondaryTextOnPrimary
        )
    }
}