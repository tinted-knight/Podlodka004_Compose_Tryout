package com.example.podlodka004.pages.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.podlodka004.Session
import com.example.podlodka004.common_views.ProfilePicture

@Composable
fun SessionCard(
    session: Session,
    isInFavorites: Boolean,
    onItemClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .clickable { onItemClick() },
        elevation = 2.dp,
        shape = MaterialTheme.shapes.large) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically) {
            ProfilePicture(
                imageUrl = session.imageUrl,
                widthDp = 50.dp,
                heightDp = 50.dp)
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.weight(1f)) {
                Text(
                    session.speaker,
                    style = MaterialTheme.typography.subtitle1)
                Text(
                    session.timeInterval,
                    style = MaterialTheme.typography.subtitle1)
                Text(
                    session.description,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis)
            }
            ButtonFavorite(isInFavorites, onFavoriteClick)
        }
    }
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
private fun ButtonFavorite(isInFavorites: Boolean, onFavoriteClick: () -> Unit) {
    IconButton(onClick = onFavoriteClick) {
        Icon(
            imageVector = when {
                isInFavorites -> Icons.Outlined.Favorite
                else -> Icons.Outlined.FavoriteBorder
            },
            tint = when {
                isInFavorites -> MaterialTheme.colors.secondary
                else -> MaterialTheme.colors.onSecondary
            },
            contentDescription = "Favorite")
    }
}