package pl.bingohood.app.place

data class Place(
    val id: String,
    val name: String,
    val lat: Double,
    val lon: Double,
    val radiusMetres: Int
)