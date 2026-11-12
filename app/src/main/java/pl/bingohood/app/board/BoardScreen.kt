package pl.bingohood.app.board

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.bingohood.app.ui.components.BingohoodButton

@Composable
fun BoardScreen(onNavigateToGuide: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
            content = { Text("Ekran planszy", fontSize = 20.sp) }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            BingohoodButton(
                text = "Zasady bingo",
                onClick = onNavigateToGuide,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
