package com.example.nomo.ui.registration

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
            painter = painterResource(id = R.drawable.ic_background_register),
            contentDescription = "Background",
            modifier = Modifier.align(Alignment.TopStart).padding(top = 50.dp)
        )

        WelcomeTextRegister(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(y = -95.dp) // Точная настройка позиции
                .fillMaxWidth(0.9f)
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
            text = "Создайте аккаунт!",
            fontFamily = SFProDisplay,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Black
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "А мы поможем с остальным",
            fontFamily = SFProText,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = SecondaryTextOnPrimary
        )
    }
}