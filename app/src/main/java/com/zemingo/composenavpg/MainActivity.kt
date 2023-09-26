package com.zemingo.composenavpg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zemingo.composenavpg.ui.Screen
import com.zemingo.composenavpg.ui.ScreenType
import com.zemingo.composenavpg.ui.theme.ComposeNAVPGTheme
import kotlin.properties.Delegates

class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val writableState = remember { object: MutableState<Int> {
                override var value: Int by Delegates.observable(0) { _, x, y -> println("$x->$y") }
                override fun component1(): Int = value
                override fun component2(): (Int) -> Unit = {
                        x -> value = x
                }
            } }
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
                                args = backStackEntry.arguments?.listOfArgs("argName", "anotherArg") ?: emptyList(),
                                state = writableState
                            )
                        }
                        composable("two/{argName}", arguments = listOf(navArgument("argName") {defaultValue = ""}),
                            enterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Companion.Down, animationSpec = tween(500)) },
                            popEnterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Companion.Up, animationSpec = tween(500)) },
                            exitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Companion.Down, animationSpec = tween(500)) },
                            popExitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Companion.Up, animationSpec = tween(500)) },
                        ) { backStackEntry ->
                            Screen(
                                ScreenType.Two,
                                navController = navController,
                                args = backStackEntry.arguments?.listOfArgs("argName") ?: emptyList(),
                                state = writableState
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
