package pl.bingohood.app.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import pl.bingohood.app.navigation.NavigationElement
import pl.bingohood.app.navigation.Route
import pl.bingohood.app.ui.theme.BingohoodTheme

@Composable
fun BingohoodBottomBar(
    currentDestination: NavDestination?,
    onNavigate: (Route) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        NavigationElement.entries.forEach { item ->
            NavigationBarItem(
                selected = currentDestination?.hasRoute(item.route::class) == true,
                label = { Text(item.label) },
                icon = { Icon(painterResource(item.icon), contentDescription = null) },
                onClick = {
                    onNavigate(item.route)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BingohoodBottomBarPreview() {
    BingohoodTheme {
        BingohoodBottomBar(
            currentDestination = null,
            onNavigate = {}
        )
    }
}
