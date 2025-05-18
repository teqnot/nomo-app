package com.example.nomo.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nomo.R
import com.example.nomo.ui.theme.SFProText
import com.example.nomo.ui.theme.SecondaryTextOnBackground
import com.example.nomo.ui.theme.SecondaryTextOnHover

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: Int,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isPasswordField: Boolean = false
) {
    var isFocused by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    val textColor = if (isFocused || value.isNotEmpty()) SecondaryTextOnHover
    else SecondaryTextOnBackground

    val finalVisualTransformation = when {
        isPasswordField && !passwordVisible -> PasswordVisualTransformation()
        else -> visualTransformation
    }

    val passwordTrailingIcon: @Composable () -> Unit = {
        IconButton(
            onClick = { passwordVisible = !passwordVisible },
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                painter = painterResource(
                    if (passwordVisible) R.drawable.ic_register_eye_off
                    else R.drawable.ic_register_eye_on
                ),
                contentDescription = "Видимость пароля",
                tint = textColor
            )
        }
    }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { isFocused = it.hasFocus },
        textStyle = LocalTextStyle.current.copy(
            color = textColor,
            fontFamily = SFProText,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = if (isPasswordField) KeyboardType.Password
            else keyboardType
        ),
        visualTransformation = finalVisualTransformation,
        decorationBox = { innerTextField ->
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = leadingIcon),
                        contentDescription = null,
                        tint = textColor
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(Modifier.weight(1f)) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                color = textColor,
                                style = LocalTextStyle.current
                            )
                        }
                        innerTextField()
                    }
                    if (isPasswordField) {
                        passwordTrailingIcon()
                    } else {
                        trailingIcon?.invoke()
                    }
                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    color = textColor,
                    thickness = 2.dp
                )
            }
        }
    )
}