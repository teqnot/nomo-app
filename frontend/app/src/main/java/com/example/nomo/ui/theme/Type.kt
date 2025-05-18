package com.example.nomo.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.nomo.R

val SFProDisplayBold = Font(R.font.sf_pro_display_bold, FontWeight.Bold)
val SFProDisplayHeavy = Font(R.font.sf_pro_display_heavy, FontWeight.ExtraBold)
val SFProDisplaySemibold = Font(R.font.sf_pro_display_semibold, FontWeight.SemiBold)
val SFProTextBold = Font(R.font.sf_pro_text_bold, FontWeight.Bold)
val SFProTextHeavy = Font(R.font.sf_pro_text_heavy, FontWeight.ExtraBold)
val SFProTextMedium = Font(R.font.sf_pro_text_medium, FontWeight.Medium)
val SFProTextSemibold = Font(R.font.sf_pro_text_semibold, FontWeight.SemiBold)

val SFProDisplay = FontFamily(
    SFProDisplayBold,
    SFProDisplayHeavy,
    SFProDisplaySemibold
)

val SFProText = FontFamily(
    SFProTextBold,
    SFProTextHeavy,
    SFProTextMedium,
    SFProTextSemibold
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = SFProText,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = SFProDisplay,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = SFProText,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)