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
    val timeLeftInSeconds: Int = 60 // only for testing
)

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