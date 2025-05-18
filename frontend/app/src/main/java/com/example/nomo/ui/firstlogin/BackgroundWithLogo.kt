import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nomo.R
import com.example.nomo.ui.firstlogin.WelcomeText

@Composable
@Preview(showBackground = true)
fun BackgroundWithLogo() {
    Box(modifier = Modifier.fillMaxSize().padding(vertical = 10.dp)) {
        // Фон
        Image(
            painter = painterResource(id = R.drawable.ic_background_first_login),
            contentDescription = "Background",
            modifier = Modifier.align(Alignment.TopStart)
        )

        // Логотип и название приложения
        TopBar(modifier = Modifier.align(Alignment.TopStart))
        WelcomeText(
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(y = 315.dp) // Точная настройка позиции
                .fillMaxWidth(0.9f)
        )

    }
}

@Composable
fun TopBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo_purple_on_white),
            contentDescription = "Logo PoW"
        )
        Image(
            painter = painterResource(id = R.drawable.ic_text_logo_purple_on_white),
            contentDescription = "Text Logo PoW"
        )
    }
}