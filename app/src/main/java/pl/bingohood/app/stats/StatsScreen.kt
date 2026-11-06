package pl.bingohood.app.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Kolory wyciągnięte do motywu
val BackgroundColor = MaterialTheme.colorScheme.background
val PrimaryNavy = MaterialTheme.colorScheme.primary
val AccentOrange = MaterialTheme.colorScheme.secondary
val SecondaryText = MaterialTheme.colorScheme.onSurfaceVariant

@Composable
fun StatsScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Nagłówek w stylu makiety
            item {
                Column(modifier = Modifier.padding(bottom = 8.dp)) {
                    Text(
                        text = "♦ Odkryj Gdańsk",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryNavy
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Twoje Statystyki",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryNavy
                    )
                }
            }

            // Kafelki ze statystykami z podświetlonymi numerkami
            item {
                StatCard(number = "1", title = "Odwiedzone miejsca", value = "12 / 25", badgeColor = PrimaryNavy)
            }
            item {
                StatCard(number = "2", title = "Zdobyte plansze Bingo", value = "3", badgeColor = PrimaryNavy)
            }
            item {
                StatCard(number = "3", title = "Najszybciej ukończone Bingo", value = "2 dni 4 godz.", badgeColor = PrimaryNavy)
            }
            item {
                StatCard(number = "4", title = "Procent ukończenia Gdańska", value = "48%", badgeColor = AccentOrange)
            }
        }
    }
}

// Komponent karty dopasowany wizualnie do zaokrągleń i stylu ze zdjęcia
@Composable
fun StatCard(number: String, title: String, value: String, badgeColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Okrągła ikona z numerkiem w stylu listy z makiety
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(color = badgeColor, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = number,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Tekst statystyki
            Column {
                Text(
                    text = title,
                    fontSize = 13.sp,
                    color = SecondaryText,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = value,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryNavy
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StatsScreenPreview() {
    StatsScreen()
}
