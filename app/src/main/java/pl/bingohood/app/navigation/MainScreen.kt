package pl.bingohood.app.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import pl.bingohood.app.achievements.AchievementsScreen
import pl.bingohood.app.board.BoardScreen
import pl.bingohood.app.guide.GuideScreen
import pl.bingohood.app.stats.StatsScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                NavigationElement.entries.forEach { item ->
                    NavigationBarItem(
                        selected = currentDestination?.hasRoute(item.route::class) == true,
                        label = { Text(item.label) },
                        icon = { Icon(painterResource(item.icon), contentDescription = null) },
                        onClick = {
                            navController.navigate(item.route)
                        }
                    )
                }
            }
        }

    ) { innerPadding ->
        NavHost(navController = navController, startDestination = BoardRoute, Modifier.padding(innerPadding)) {

            composable<BoardRoute> {
                BoardScreen(
                    onNavigateToGuide = {
                        navController.navigate(GuideRoute)
                    },
                    onBoardDiscover = {
                        // TODO
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