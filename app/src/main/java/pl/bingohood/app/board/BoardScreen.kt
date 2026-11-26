package pl.bingohood.app.board

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
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

            if (boardPhase == BoardPhase.UNDISCOVERED) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = MaterialTheme.colorScheme.secondaryContainer
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_auto_awesome_24),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Nowa plansza gotowa",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                    Text(
                        text = "Kliknij by odkryć planszę na dziś",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "gówno 1234, zrob zdjecie atrakcji lub kutasa",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Zrob sweet focie gołego neptuna",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        BingoProgressTracker(maxInLine = maxInLine)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

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
                    BoardTimerCard(timeLeftInSeconds = timeLeftInSeconds)
                } else {
                    BingohoodButton(
                        text = "Odkryj planszę",
                        onClick = onBoardDiscover,
                        modifier = Modifier.fillMaxWidth(),
                        iconRes = R.drawable.star_border,
                        iconTint = MaterialTheme.colorScheme.secondary
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
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_info_24),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
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
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(50),
            color = MaterialTheme.colorScheme.secondaryContainer,
            border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.secondary)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.achievements),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Masz BINGO!",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Icon(
                    painter = painterResource(R.drawable.baseline_auto_awesome_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(24.dp)
                )
            }
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
fun BoardTimerCard(
    timeLeftInSeconds: Int,
    modifier: Modifier = Modifier
) {
    val hours = timeLeftInSeconds / 3600
    val minutes = (timeLeftInSeconds % 3600) / 60
    val seconds = timeLeftInSeconds % 60

    val formattedHours = hours.toString().padStart(2, '0')
    val formattedMinutes = minutes.toString().padStart(2, '0')
    val formattedSeconds = seconds.toString().padStart(2, '0')

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Lewa strona - Ikona i teksty
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.baseline_schedule_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Nowa plansza",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = "pojawi się za",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                    )
                }
            }

            // Prawa strona - Zegar cyfrowy
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                TimerDigitBox(text = formattedHours, textColor = MaterialTheme.colorScheme.onPrimary)
                Text(":", color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
                TimerDigitBox(text = formattedMinutes, textColor = MaterialTheme.colorScheme.onPrimary)
                Text(":", color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
                TimerDigitBox(text = formattedSeconds, textColor = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}

@Composable
private fun TimerDigitBox(text: String, textColor: androidx.compose.ui.graphics.Color) {
    Box(
        modifier = Modifier
            .height(48.dp)
            .width(44.dp)
            .background(
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.12f),
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )
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
        isBingoLine || isVisited -> MaterialTheme.colorScheme.primary
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
                fontWeight = if (isBingoLine || isVisited) FontWeight.SemiBold else if (currentText == UNKNOWN_PLACE) FontWeight.Bold else FontWeight.Medium,
                color = textColor,
                textAlign = TextAlign.Center,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}