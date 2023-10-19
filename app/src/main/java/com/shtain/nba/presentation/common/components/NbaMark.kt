package com.shtain.nba.presentation.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shtain.nba.R


@Composable
fun NbaMark(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier = Modifier
                .wrapContentWidth(Alignment.End)
                .padding(top = 8.dp)
                .width(48.dp)
                .height(48.dp),

            painter = painterResource(id = R.drawable.ic_nba_ball),
            contentDescription = null
        )
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.colorTextBase),
            modifier = Modifier
                .padding(2.dp)
                .wrapContentWidth(Alignment.End)
        )
    }

}

@Preview
@Composable
private fun PlayerDetailsPreview() {
    MaterialTheme {
        NbaMark()
    }
}