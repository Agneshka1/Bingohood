package pl.bingohood.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import pl.bingohood.app.navigation.MainScreen
import pl.bingohood.app.ui.theme.BingohoodTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BingohoodTheme {
                MainScreen()
            }
        }
    }
}
