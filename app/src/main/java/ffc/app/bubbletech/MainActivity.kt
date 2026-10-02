package ffc.app.bubbletech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import ffc.app.bubbletech.navigation.AppNavigation
import ffc.app.bubbletech.ui.theme.BubbleTechTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        //val appReady = false
        //splashScreen.setKeepOnScreenCondition{
          //  !appReady
        //}

        enableEdgeToEdge()
        setContent {
            BubbleTechTheme {
                AppNavigation()
            }
        }
    }
}