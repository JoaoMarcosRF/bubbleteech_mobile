package ffc.app.bubbletech.navigation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ffc.app.bubbletech.R
import ffc.app.bubbletech.data.FakeRepository
import ffc.app.bubbletech.screens.HomeScreen

import ffc.app.bubbletech.screens.LoginScreen
import ffc.app.bubbletech.screens.RegisterScreen
import ffc.app.bubbletech.ui.theme.BackgroundBlue
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundBlue)
    ) {
        NavHost(
            navController = navController,
            startDestination = "splash_screen"
        ) {

            composable("splash_screen") {
                SplashScreen(navController = navController)
            }

            composable("home_screen") {
                HomeScreen(
                    posts = FakeRepository().getPosts()
                )
            }

            composable(route = "register_screen") {
                RegisterScreen(){}
            }

            composable("login"){
                LoginScreen(
                    onLogin = {
                        navController.navigate("home_screen") {

                            popUpTo("login") {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
    }
}

// Mudar a splash de arquivo depois.
@Composable
fun SplashScreen(navController: NavController) {
    val opacity = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        opacity.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 700)
        )

        delay(1500.milliseconds)

        opacity.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 700)
        )

        navController.navigate("login") {
            popUpTo("splash_screen") {
                inclusive = true
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF00174E))
    ) {
        Image(
            painter = painterResource(R.drawable.ic_logo),
            contentDescription = "Logo",
            modifier = Modifier
                .width(280.dp)
                .alpha(opacity.value)
        )
    }
}