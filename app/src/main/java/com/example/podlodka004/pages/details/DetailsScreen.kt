package com.example.podlodka004.pages.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.podlodka004.Session
import com.example.podlodka004.common_views.ProfilePicture

@Composable
fun DetailsScreen(session: Session, onUpClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(session.speaker) },
                navigationIcon = {
                    IconButton(onClick = onUpClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Navigate Back"
                        )
                    }
                },
                elevation = 0.dp,
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(state = rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ProfilePicture(
                imageUrl = session.imageUrl,
                widthDp = 200.dp,
                heightDp = 200.dp
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = session.speaker,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Icon(
                    imageVector = Icons.Outlined.DateRange,
                    contentDescription = "Date range"
                )
                Text(text = "${session.date}, ${session.timeInterval}")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = session.description,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}