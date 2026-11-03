package pl.bingohood.app.board

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import pl.bingohood.app.place.Place

data class BoardUiState(
    val boardPhase: BoardPhase = BoardPhase.UNDISCOVERED,
    val places: List<Place> = emptyList()
)

class BoardViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BoardUiState())
    val uiState: StateFlow<BoardUiState> = _uiState.asStateFlow()

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

    fun discoverBoard() {
        val testPlaces = generateTestPlaces()

        _uiState.update { currentState ->
            currentState.copy(
                boardPhase = BoardPhase.DISCOVERED,
                places = testPlaces
            )
        }
    }
}