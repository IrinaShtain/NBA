package com.shtain.nba.presentation.common.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.shtain.nba.R

@Composable
fun CircularProgress(paddingValues: PaddingValues) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
                .padding(8.dp)
                .padding(paddingValues = paddingValues),
            color = colorResource(R.color.colorSecondary),
        )
    }
}