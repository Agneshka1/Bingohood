package pl.bingohood.app.guide

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.bingohood.app.ui.components.BingohoodButton
import pl.bingohood.app.ui.components.BingohoodHeader
import pl.bingohood.app.ui.theme.BingohoodTheme
import pl.bingohood.app.ui.theme.DividerColor
import pl.bingohood.app.ui.theme.FooterTextColor
import pl.bingohood.app.ui.theme.GrayCell
import pl.bingohood.app.ui.theme.NavyBlue
import pl.bingohood.app.ui.theme.OrangeAccent
import pl.bingohood.app.ui.theme.SubtitleColor

private val InstructionSteps = listOf(
    "Wybierz pole na planszy i udaj się do wskazanego miejsca.",
    "Dotrzyj do wyznaczonej lokalizacji w Gdańsku.",
    "Oznacz miejsce jako zaliczone",
    "Zaliczenie trzech pól w jednej linii daje bingo.",
)

@Composable
fun GuideScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HeaderSection()
            BingoPatternsCard()
            InstructionsCard()
            Spacer(modifier = Modifier.height(4.dp))
            BingohoodButton(
                text = "Rozumiem",
                onClick = onNavigateBack,
                modifier = Modifier.fillMaxWidth()
            )
            FooterSection()
        }
    }
}

@Composable
private fun HeaderSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        BingohoodHeader()

        Text(
            text = "Jak zdobyć bingo?",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            ),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun BingoPatternsCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Układy bingo",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = NavyBlue
                )
                Text(
                    text = "8 możliwych linii",
                    style = MaterialTheme.typography.bodySmall,
                    color = SubtitleColor
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BingoPatternItem(
                    title = "W rzędzie",
                    isMarked = { row, _ -> row == 0 }
                )
                BingoPatternItem(
                    title = "W kolumnie",
                    isMarked = { _, col -> col == 1 }
                )
                BingoPatternItem(
                    title = "Po przekątnej",
                    isMarked = { row, col -> row == col }
                )
            }
        }
    }
}

@Composable
private fun BingoPatternItem(
    title: String,
    isMarked: (row: Int, col: Int) -> Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        BingoGrid3x3(isMarked = isMarked)
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Medium,
            color = NavyBlue
        )
    }
}

@Composable
private fun BingoGrid3x3(
    isMarked: (row: Int, col: Int) -> Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        for (row in 0 until 3) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                for (col in 0 until 3) {
                    val marked = isMarked(row, col)
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(if (marked) OrangeAccent else GrayCell)
                    )
                }
            }
        }
    }
}

@Composable
private fun InstructionsCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            InstructionSteps.forEachIndexed { index, text ->
                val stepNumber = index + 1
                val isLastStep = stepNumber == InstructionSteps.size

                InstructionStepRow(
                    stepNumber = stepNumber,
                    text = text,
                    isHighlight = isLastStep
                )

                if (!isLastStep) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = DividerColor,
                        thickness = 1.dp
                    )
                }
            }
        }
    }
}

@Composable
private fun InstructionStepRow(
    stepNumber: Int,
    text: String,
    modifier: Modifier = Modifier,
    isHighlight: Boolean = false
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(if (isHighlight) OrangeAccent else NavyBlue),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stepNumber.toString(),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = NavyBlue,
            lineHeight = 20.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun FooterSection(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(18.dp)
                .clip(CircleShape)
                .background(FooterTextColor.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "i",
                color = FooterTextColor,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }
        Text(
            text = "Po zdobyciu bingo możesz dalej odkrywać pozostałe miejsca na planszy.",
            style = MaterialTheme.typography.bodySmall,
            color = FooterTextColor,
            lineHeight = 16.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GuideScreenPreview() {
    BingohoodTheme {
        GuideScreen()
    }
}
