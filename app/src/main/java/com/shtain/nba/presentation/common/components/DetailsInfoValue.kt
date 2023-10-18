package com.shtain.nba.presentation.common.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shtain.nba.R


@Composable
fun DetailsInfoValue(resHint: Int, value: String, onValueClick: (() -> Unit)? = null) {
    Row(Modifier.wrapContentWidth()) {
        Text(
            text = stringResource(id = resHint),
            color = colorResource(R.color.colorTextBase),
            fontSize = 15.sp,
            modifier = Modifier
                .padding(
                    start = 8.dp,
                    top = 8.dp,
                    bottom = 8.dp
                )
                .wrapContentWidth(Alignment.Start)
        )
        onValueClick?.let {
            ClickableText(
                text = AnnotatedString(value),
                onClick = { onValueClick.invoke() },
                style = TextStyle(
                    color = colorResource(R.color.colorSecondary),
                    fontSize = 18.sp,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Cursive
                ),
                modifier = Modifier
                    .padding(
                        start = 4.dp,
                        end = 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .wrapContentWidth(Alignment.End)
            )
        } ?: kotlin.run {
            Text(
                text = value,
                fontSize = 15.sp,
                color = colorResource(R.color.colorTextBase),
                modifier = Modifier
                    .padding(
                        start = 4.dp,
                        end = 8.dp,
                        top = 8.dp
                    )
                    .wrapContentWidth(Alignment.End)
            )
        }
    }

}