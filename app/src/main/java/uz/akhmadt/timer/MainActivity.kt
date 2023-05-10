 
package uz.akhmadt.timer

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateColorAsState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import uz.akhmadt.timer.timer.TimerScreen
import uz.akhmadt.timer.timer.TimerViewModel
import uz.akhmadt.timer.ui.setSystemBarsColor
import uz.akhmadt.timer.ui.theme.Colors
import uz.akhmadt.timer.ui.theme.MyTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: TimerViewModel = viewModel()
            MyTheme(darkMode = viewModel.darkMode) {
                val systemBarsColor by animateColorAsState(Colors.systemBars)
                setSystemBarsColor(systemBarsColor)

                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {
    val backgroundColor by animateColorAsState(MaterialTheme.colors.background)
    Surface(color = backgroundColor) {
        TimerScreen()
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkMode = true) {
        MyApp()
    }
}
