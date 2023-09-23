package com.zemingo.composenavpg.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.zemingo.composenavpg.R

sealed class ScreenType(val nameRes: Int, val color: Color) {
    object One : ScreenType(R.string.composable_one, Color.Red)
    object Two : ScreenType(R.string.composable_two, Color.Green)
}

@Composable
fun Screen(type: ScreenType, navController: NavHostController, args: List<String>) {
    ScreenDetailed(
        name = stringResource(id = type.nameRes),
        color = type.color,
        navController = navController,
        args = args
    )
}

@Composable
fun ScreenDetailed(name: String, color: Color, navController: NavHostController, args: List<String>) {
    val navArguments = remember(args) { args }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color)
    ) {
        Text(
            text = navArguments.joinToString("/")
        )
        Text(
            text = name
        )
        Button(onClick = { navController.navigate("one?argName=From${name.trim()}&anotherArg=WithAnotherArg") }) {
            Text(text = "Navigate to ComposableOne")
        }
        Button(onClick = { navController.navigate("two/From${name.trim()}") }) {
            Text(text = "Navigate to ComposableTwo")
        }
    }
}
