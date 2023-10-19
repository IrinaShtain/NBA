package com.shtain.nba.presentation.common.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shtain.nba.R


@Composable
fun Name(name: String) {
    Text(
        text = name,
        color = colorResource(R.color.colorTextBase),
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier
            .wrapContentWidth()
            .padding(horizontal = 8.dp)
            .wrapContentWidth(Alignment.Start)
    )
}