package com.shtain.nba.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.shtain.nba.R


@Composable
fun NbaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val lightColors = lightColors(
        primary = colorResource(id = R.color.red_700),
        primaryVariant = colorResource(id = R.color.red_900),
        secondary = colorResource(id = R.color.teal_200),
        background = colorResource(id = R.color.white),
        onPrimary = colorResource(id = R.color.white),
        onSecondary = colorResource(id = R.color.black),
    )
    val darkColors = darkColors(
        primary = colorResource(id = R.color.red_400),
        primaryVariant = colorResource(id = R.color.red_500),
        secondary = colorResource(id = R.color.teal_200),
        onPrimary = colorResource(id = R.color.black),
        onSecondary = colorResource(id = R.color.black),
        onBackground = colorResource(id = R.color.black),
        surface = colorResource(id = R.color.black),
        onSurface = colorResource(id = R.color.white),
    )
    val colors = if (darkTheme) darkColors else lightColors
    MaterialTheme(
        colors = colors,
        content = content
    )
}