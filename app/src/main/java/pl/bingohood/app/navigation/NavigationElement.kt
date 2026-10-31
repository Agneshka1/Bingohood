package pl.bingohood.app.navigation

import pl.bingohood.app.R

enum class NavigationElement(val label: String, val icon: Int, val route: Route) {

    BOARD("Plansza", R.drawable.board, BoardRoute),
    STATS("Statystyki", R.drawable.stats, StatsRoute),
    ACHIEVEMENTS("Osiągnięcia", R.drawable.achievements, AchievementsRoute)
}