package com.example.nomo.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nomo.R
import com.example.nomo.ui.theme.PrimaryColor

@Composable
@Preview(showBackground = true)
fun BottomBar() {
    HorizontalDivider(
        thickness = 3.dp,
        color = PrimaryColor,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(shape = RoundedCornerShape(10.dp)))
    BottomAppBar(
        backgroundColor = Color.Transparent,
        contentPadding = PaddingValues(horizontal = 16.dp),
        elevation = 0.dp
    ) {
        BottomBarItem(R.drawable.ic_calendar)
        Spacer(modifier = Modifier.weight(1f))
        BottomBarItem(R.drawable.ic_add)
        Spacer(modifier = Modifier.weight(1f))
        BottomBarItem(R.drawable.ic_account)
    }
}

@Composable
fun BottomBarItem(iconRes: Int) {
    IconButton(onClick = { /* TODO: Handle Click */ }) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = "Bottom Bar Icon"
        )
    }
}