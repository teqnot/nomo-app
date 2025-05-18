package com.example.nomo.ui.firstlogin

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nomo.ui.theme.Black
import com.example.nomo.ui.theme.SFProDisplay
import com.example.nomo.ui.theme.SFProText
import com.example.nomo.ui.theme.SecondaryTextOnPrimary

@Composable
fun WelcomeText(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Делиться - просто.",
            fontFamily = SFProDisplay,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Black
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Зарегистрируйтесь и узнайте, как мы можем вам помочь",
            fontFamily = SFProText,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = SecondaryTextOnPrimary,
            modifier = Modifier.fillMaxWidth(0.8f)
        )
    }
}