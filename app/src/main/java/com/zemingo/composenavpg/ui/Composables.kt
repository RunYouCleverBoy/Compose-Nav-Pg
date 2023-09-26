package com.zemingo.composenavpg.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.zemingo.composenavpg.R

sealed class ScreenType(val nameRes: Int, val color: Color) {
    object One : ScreenType(R.string.composable_one, Color.Red)
    object Two : ScreenType(R.string.composable_two, Color.Green)
}

@Composable
fun Screen(type: ScreenType, navController: NavHostController, args: List<String>, state: MutableState<Int>) {
    ScreenDetailed(
        name = stringResource(id = type.nameRes),
        color = type.color,
        navController = navController,
        state = state,
        args = args
    )
}

@Composable
fun ScreenDetailed(name: String, color: Color, navController: NavHostController, state: MutableState<Int>, args: List<String>) {
    var stateData by state
    val navArguments = remember(args) { args }
    val nameText by remember { derivedStateOf { "$name (${stateData.unaryFives})" } }
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
            text = nameText
        )
        Button(onClick = {
            stateData++
            navController.navigate("one?argName=From${name.trim()}&anotherArg=WithAnotherArg")
        }) {
            Text(text = "Navigate to ComposableOne")
        }
        Button(onClick = {
            stateData = -stateData
            navController.navigate("two/From${name.trim()}")
        }) {
            Text(text = "Navigate to ComposableTwo")
        }
        LazyVerticalGrid(columns = GridCells.Fixed(3), content = {
            items(100) {
                Box(modifier = Modifier
                    .background(color = Color((it * 0x10B070L or 0x80000000L).toInt()))
                    .fillMaxWidth()
                    .height(100.dp), contentAlignment = Alignment.Center) {
                    Text(text = it.toString())
                }
            }
        })
    }
}

val Int.unaryFives: String
    get() = when {
        this >= 0 -> "///// ".repeat(this / 5) + "|".repeat(this % 5)
        else -> "🤬 ".repeat(-this / 5) + "🙈".repeat(-this % 5)
    }