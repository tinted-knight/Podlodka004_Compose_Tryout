package com.example.podlodka004.common_views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import coil.transform.CircleCropTransformation
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun ProfilePicture(imageUrl: String, widthDp: Dp, heightDp: Dp) {
    Image(
        modifier = Modifier
            .width(widthDp)
            .height(heightDp),
        painter = rememberCoilPainter(
            imageUrl,
            requestBuilder = { transformations(CircleCropTransformation()) }),
        contentDescription = "Profile picture")
}