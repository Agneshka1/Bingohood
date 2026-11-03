package pl.bingohood.app.board

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BoardScreen(onNavigateToGuide: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Ekran planszy",
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.Center)
        )

        Button(
            onClick = onNavigateToGuide,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Zasady bingo")
        }
    }
}