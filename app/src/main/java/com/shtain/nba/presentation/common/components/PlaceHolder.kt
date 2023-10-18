package com.shtain.nba.presentation.common.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shtain.nba.R


@Composable
fun PlaceHolderState(
    paddingValues: PaddingValues,
    titleRes: Int,
    descriptionRes: Int,
    onReloadClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.colorCardBackground),
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        ),
        border = BorderStroke(1.dp, Color.Black),
        modifier = Modifier
            .wrapContentHeight(Alignment.CenterVertically)
            .fillMaxWidth()
            .padding(paddingValues)
            .padding(start = 20.dp, end = 20.dp),
        shape = RoundedCornerShape(20.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(8.dp)
                    .width(154.dp)
                    .height(154.dp),
                painter = painterResource(id = R.drawable.ic_no_connection),
                contentDescription = null
            )

            Text(
                textAlign = TextAlign.Center,
                text = stringResource(id = titleRes),
                color = colorResource(R.color.colorTextBase),
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            Text(
                textAlign = TextAlign.Center,
                text = stringResource(id = descriptionRes),
                color = colorResource(R.color.colorTextBase),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            OutlinedButton(
                onClick = { onReloadClick() },
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(8.dp)
                    .align(CenterHorizontally)
            ) {
                Text(text = stringResource(id = R.string.reload))
            }
        }
    }
}

@Preview
@Composable
private fun PlaceHolderPreview() {
    MaterialTheme {
        PlaceHolderState(
            PaddingValues(),
            R.string.no_internet_connection,
            R.string.check_network_connection,
            {

            }
        )
    }
}