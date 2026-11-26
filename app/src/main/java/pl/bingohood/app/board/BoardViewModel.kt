package pl.bingohood.app.board

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.bingohood.app.place.Place
import kotlin.time.Duration.Companion.seconds

data class BoardUiState(
    val boardPhase: BoardPhase = BoardPhase.UNDISCOVERED,
    val places: List<Place> = emptyList(),
    val timeLeftInSeconds: Int = 60, // only for testing
    val visitedIndices: Set<Int> = emptySet()
) {
    // Zwraca maksymalną ilość trafień w ramach jednej, 3-elementowej linii
    val maxInLine: Int
        get() {
            val lines = listOf(
                listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8),
                listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8),
                listOf(0, 4, 8), listOf(2, 4, 6)
            )
            return lines.maxOfOrNull { line -> line.count { it in visitedIndices } } ?: 0
        }

    // Zwraca indeksy tych kafelków, które utworzyły pełne, 3-elementowe linie
    val bingoLineIndices: Set<Int>
        get() {
            val lines = listOf(
                listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8),
                listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8),
                listOf(0, 4, 8), listOf(2, 4, 6)
            )
            return lines.filter { line -> line.count { it in visitedIndices } == 3 }
                .flatten()
                .toSet()
        }
}

class BoardViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BoardUiState())
    val uiState: StateFlow<BoardUiState> = _uiState.asStateFlow()

    private fun startTimer() {
        viewModelScope.launch {
            var secondsLeft = uiState.value.timeLeftInSeconds

            while(secondsLeft > 0) {
                _uiState.update {
                    it.copy(timeLeftInSeconds = secondsLeft--)
                }
                delay(1.seconds)
            }

            resetBoard()
        }
    }

    private fun resetBoard() {
        _uiState.value = BoardUiState()
    }

    fun discoverBoard() {
        val testPlaces = generateTestPlaces()

        _uiState.update { currentState ->
            currentState.copy(
                boardPhase = BoardPhase.DISCOVERED,
                places = testPlaces
            )
        }
        startTimer()
    }


    fun toggleTileVisited(index: Int) {
        _uiState.update { currentState ->
            if (currentState.boardPhase != BoardPhase.DISCOVERED) return@update currentState

            val newVisited = if (index in currentState.visitedIndices) {
                currentState.visitedIndices - index
            } else {
                currentState.visitedIndices + index
            }
            currentState.copy(visitedIndices = newVisited)
        }
    }

    private fun generateTestPlaces() : List<Place> {
        val testPlaces = mutableListOf<Place>()

        for (i in 1..9) {
            testPlaces.add(Place(
                "test-$i",
                "Dworzec PKP Gdańsk Główny",
                lat = 54.35564348681012,
                lon = 18.644546903071603,
                radiusMetres = 100)
            )
        }

        return testPlaces // 9 elements with the same data, but with different ids (only for testing)
    }
}