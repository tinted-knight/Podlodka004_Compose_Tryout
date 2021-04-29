package com.example.podlodka004.pages.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.podlodka004.Session

@Composable
fun StarredView(values: List<Session>) {
    if (values.isNotEmpty()) {
        Column {
            Text(
                "Starred",
                modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.subtitle1)
            LazyRow(
                modifier = Modifier.heightIn(50.dp, 150.dp)
            ) {
                items(values) {
                    StarredCard(it)
                }
            }
        }
    }
}

@Composable
private fun StarredCard(session: Session) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .fillMaxHeight()
            .padding(4.dp),
        shape = MaterialTheme.shapes.small,
        elevation = 2.dp,
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
        ) {
            Text(
                session.timeInterval,
                style = MaterialTheme.typography.subtitle1)
            Text(session.date)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                session.speaker,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.subtitle1)
            Text(
                session.description,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}