package pl.bingohood.app.board

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.bingohood.app.place.Place

@Composable
fun BoardScreen(boardPhase: BoardPhase, places: List<Place>, onNavigateToGuide: () -> Unit, onBoardDiscover: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Ekran planszy",
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.Center)
        )

        Column(
            Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            Arrangement.spacedBy(16.dp)
        ) {
            FilledTonalButton(
                onClick = onBoardDiscover,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (boardPhase == BoardPhase.DISCOVERED) Text("Timer") else Text("Odkryj planszę")
            }

            Button(
                onClick = onNavigateToGuide,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Zasady bingo")
            }
        }
    }
}