package com.example.podlodka004.pages.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.podlodka004.MainViewModel
import com.example.podlodka004.MessageEvent
import com.example.podlodka004.Session

@ExperimentalFoundationApi
@Composable
fun MainScreen(viewModel: MainViewModel) {
    val sessions: List<Session> by viewModel.filtered.observeAsState(emptyList())
    val searchString: String by viewModel.searchString.observeAsState("")
    val starredSession: List<Session> by viewModel.starredSessions.observeAsState(emptyList())

    val message: MessageEvent by viewModel.messages.observeAsState(MessageEvent.Idle)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Jetpack Kolhoz") },
                elevation = 0.dp,
            )
        },
    ) {
        Box {
            LazyColumn(modifier = Modifier.padding(8.dp)) {
                item {
                    SearchView(
                        searchString,
                        onValueChange = viewModel::searchStringChange,
                    )
                }
                item {
                    StarredView(values = starredSession)
                }
                item {
                    Text(
                        "Sessions",
                        modifier = Modifier.padding(4.dp),
                        style = MaterialTheme.typography.subtitle1
                    )
                }
                sessions.groupBy { it.date }.forEach { groupedSessions ->
                    stickyHeader {
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color.Black.copy(alpha = 0.75f))
                        ) {
                            Text(
                                groupedSessions.key,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                color = Color.White,
                            )
                        }
                    }
                    items(groupedSessions.value) {
                        SessionCard(
                            session = it,
                            isInFavorites = viewModel.checkFavorite(it).observeAsState(false).value,
                            onItemClick = { viewModel.sessionClick(it) },
                            onFavoriteClick = { viewModel.toggleFavorite(it) },
                        )
                    }
                }
            }

            when (message) {
                MessageEvent.Idle -> {
                }
                MessageEvent.StarredOverflow -> {
                    Snackbar(
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.BottomCenter)
                    ) {
                        Text(text = "Favorites overflow")
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchView(searchString: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = searchString,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search"
            )
        },
        label = { Text(text = "Type to search") },
        modifier = Modifier
            .fillMaxWidth()
    )
}