//package com.example.nomo.ui.common
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.example.nomo.R
//import com.example.nomo.ui.theme.PrimaryColor
//
//@Composable
//@Preview(showBackground = true)
//fun TopBar() {
//    TopAppBar(
//        backgroundColor = PrimaryColor,
//        contentPadding = PaddingValues(horizontal = 5.dp, vertical = 5.dp),
//        modifier = Modifier
//            .padding(horizontal = 10.dp, vertical = 8.dp)
//            .clip(shape = RoundedCornerShape(10.dp))
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.ic_logo),
//            contentDescription = "App Logo",
//        )
//        Spacer(modifier = Modifier.width(8.dp))
//        Image(
//            painter = painterResource(id = R.drawable.ic_text_logo),
//            contentDescription = "App Logo Text",
//        )
//        Spacer(modifier = Modifier.weight(1f))
//        IconButton(onClick = { /*TODO: Handle menu click*/ }) {
//            Image(
//                painter = painterResource(id = R.drawable.ic_menu),
//                contentDescription = "Menu"
//            )
//        }
//    }
//}