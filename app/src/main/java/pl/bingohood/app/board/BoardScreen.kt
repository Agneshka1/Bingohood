package pl.bingohood.app.board

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.bingohood.app.place.Place

@Composable
fun BoardScreen(boardPhase: BoardPhase, places: List<Place>, onNavigateToGuide: () -> Unit, onBoardDiscover: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            Arrangement.spacedBy(16.dp)
        ) {
            var index = 0

            for (row in 0..2) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    for (i in 1..3) {
                        val placeName = places.getOrNull(index)?.name ?: "?"
                        BoardTile(
                            text = placeName,
                            modifier = Modifier.weight(1f).aspectRatio(1f)
                        )
                        index++
                    }
                }
            }

            FilledTonalButton(
                onClick = onBoardDiscover,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
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

@Composable
fun BoardTile(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge
) {
    Card(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .size(90.dp)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = if (text == "?") 20.sp else 15.sp,
                style = textStyle,
                textAlign = TextAlign.Center,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}