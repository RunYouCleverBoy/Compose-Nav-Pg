package com.zemingo.composenavpg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zemingo.composenavpg.ui.Screen
import com.zemingo.composenavpg.ui.ScreenType
import com.zemingo.composenavpg.ui.theme.ComposeNAVPGTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNAVPGTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "one") {
                        composable("one?argName={argName}&anotherArg={anotherArg}",
                            arguments = listOf("argName", "anotherArg").map { navArgument(it) {defaultValue = ""} },
                            enterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right, animationSpec = tween(500)) },
                            popEnterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left, animationSpec = tween(500)) },
                            exitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right, animationSpec = tween(500)) },
                            popExitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left, animationSpec = tween(500)) },
                        ) { backStackEntry ->
                            Screen(
                                ScreenType.One,
                                navController = navController,
                                args = backStackEntry.arguments?.listOfArgs("argName", "anotherArg") ?: emptyList()
                            )
                        }
                        composable("two/{argName}", arguments = listOf(navArgument("argName") {defaultValue = ""}),
                            enterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right, animationSpec = tween(500)) },
                            popEnterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left, animationSpec = tween(500)) },
                            exitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right, animationSpec = tween(500)) },
                            popExitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left, animationSpec = tween(500)) },
                        ) { backStackEntry ->
                            Screen(
                                ScreenType.Two,
                                navController = navController,
                                args = backStackEntry.arguments?.listOfArgs("argName") ?: emptyList()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeNAVPGTheme {
        Greeting("Android")
    }
}

private inline fun <reified T> Bundle?.listOfArgs(vararg strings: String): List<T> = strings.mapNotNull { this?.getString(it) as? T }
