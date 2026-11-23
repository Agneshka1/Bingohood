package pl.bingohood.app.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import pl.bingohood.app.achievements.AchievementsScreen
import pl.bingohood.app.board.BoardScreen
import pl.bingohood.app.guide.GuideScreen
import pl.bingohood.app.stats.StatsScreen
import pl.bingohood.app.ui.components.BingohoodBottomBar

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BingohoodBottomBar(
                currentDestination = currentDestination,
                onNavigate = { route ->
                    navController.navigate(route)
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BoardRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<BoardRoute> {
                BoardScreen(
                    onNavigateToGuide = {
                        navController.navigate(GuideRoute)
                    }
                )
            }

            composable<StatsRoute> { StatsScreen() }
            composable<AchievementsRoute> { AchievementsScreen() }

            composable<GuideRoute> {
                GuideScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
