package pl.bingohood.app.board

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.bingohood.app.R
import pl.bingohood.app.place.Place
import pl.bingohood.app.ui.components.BingohoodButton
import pl.bingohood.app.ui.components.BingohoodHeader

const val UNKNOWN_PLACE = "?"

@Composable
fun BoardScreen(
    boardPhase: BoardPhase,
    places: List<Place>,
    timeLeftInSeconds: Int,
    onNavigateToGuide: () -> Unit,
    onBoardDiscover: () -> Unit,
    modifier: Modifier = Modifier,
    visitedIndices: Set<Int> = emptySet(),
    maxInLine: Int = 0,
    bingoLineIndices: Set<Int> = emptySet(),
    onTileClick: (Int) -> Unit = {}
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BingohoodHeader(subtitle = "Zdobądź bingo!")

            Spacer(modifier = Modifier.height(24.dp))

            if (boardPhase == BoardPhase.DISCOVERED) {
                BingoProgressTracker(maxInLine = maxInLine)
            } else {
                Spacer(modifier = Modifier.height(72.dp))
            }

            Spacer(modifier = Modifier.height(32.dp))

            var index = 0
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                repeat(3) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        repeat(3) {
                            val currentIndex = index
                            val placeName = places.getOrNull(currentIndex)?.name ?: UNKNOWN_PLACE

                            BoardTile(
                                placeName = placeName,
                                boardPhase = boardPhase,
                                index = currentIndex,
                                isVisited = currentIndex in visitedIndices,
                                isBingoLine = currentIndex in bingoLineIndices,
                                enabled = boardPhase == BoardPhase.DISCOVERED,
                                onClick = { onTileClick(currentIndex) },
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                            )
                            index++
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (boardPhase == BoardPhase.DISCOVERED) {
                    OutlinedButton(
                        onClick = onBoardDiscover,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                        colors = androidx.compose.material3.ButtonDefaults.outlinedButtonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.onSurface,
                            disabledContainerColor = MaterialTheme.colorScheme.surface,
                            disabledContentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        enabled = false
                    ) {
                        Text(
                            text = timeLeftInSeconds.toString(),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                } else {
                    BingohoodButton(
                        text = "Odkryj planszę",
                        onClick = onBoardDiscover,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                OutlinedButton(
                    onClick = onNavigateToGuide,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Text(
                        text = "Zasady gry",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun BingoProgressTracker(maxInLine: Int) {
    if (maxInLine >= 3) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondaryContainer, shape = CircleShape)
                .border(1.dp, MaterialTheme.colorScheme.secondary, shape = CircleShape)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(R.drawable.achievements),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(24.dp)
            )
            Text("Masz BINGO!", color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Icon(
                painter = painterResource(R.drawable.ic_star),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(24.dp)
            )
        }
    } else {
        val text = when (maxInLine) {
            1 -> "Jeszcze 2 miejsca do BINGO"
            2 -> "Już tylko 1 miejsce do BINGO!"
            else -> "3 miejsca do BINGO"
        }
        val textColor = if (maxInLine == 0) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text, color = textColor, fontWeight = FontWeight.Medium)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (i in 0 until 3) {
                    val color = when {
                        i < maxInLine -> if (maxInLine == 1) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.secondary
                        else -> MaterialTheme.colorScheme.surfaceVariant
                    }
                    Box(
                        modifier = Modifier
                            .height(8.dp)
                            .weight(1f)
                            .background(color, CircleShape)
                    )
                }
            }
        }
    }
}

@Composable
fun BoardTile(
    placeName: String,
    boardPhase: BoardPhase,
    index: Int,
    isVisited: Boolean,
    isBingoLine: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Animacja obrotu Y od 0 do 180 z kaskadowym opóźnieniem
    val targetRotation = if (boardPhase == BoardPhase.DISCOVERED) 180f else 0f
    val rotation by animateFloatAsState(
        targetValue = targetRotation,
        animationSpec = tween(
            durationMillis = 400,
            delayMillis = index * 50
        ),
        label = "flipAnimation"
    )

    // Jeśli obrót przekroczył 90 stopni, kafelek jest "odkryty"
    val isFlipped = rotation > 90f
    val currentText = if (isFlipped) placeName else UNKNOWN_PLACE

    val bgColor = when {
        isBingoLine -> MaterialTheme.colorScheme.secondaryContainer
        isVisited -> MaterialTheme.colorScheme.tertiaryContainer
        else -> MaterialTheme.colorScheme.surface
    }
    val borderColor = when {
        isBingoLine -> MaterialTheme.colorScheme.secondary
        isVisited -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.outlineVariant
    }
    val textColor = when {
        isBingoLine -> MaterialTheme.colorScheme.onSecondaryContainer
        isVisited -> MaterialTheme.colorScheme.onTertiaryContainer
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    Card(
        modifier = modifier
            .clickable(enabled = enabled, onClick = onClick)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        border = BorderStroke(1.dp, borderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .graphicsLayer {
                    // Jeśli kafelek jest odwrócony, obracamy zawartość z powrotem, aby nie była lustrzanym odbiciem
                    if (isFlipped) {
                        rotationY = 180f
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = currentText,
                fontSize = if (currentText == UNKNOWN_PLACE) 24.sp else 14.sp,
                fontWeight = if (currentText == UNKNOWN_PLACE) FontWeight.Bold else FontWeight.Medium,
                color = textColor,
                textAlign = TextAlign.Center,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}