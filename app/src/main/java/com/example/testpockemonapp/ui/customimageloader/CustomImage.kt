package com.example.testpockemonapp.ui.customimageloader

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity

@Composable
fun CustomImage(
    url: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
) {
    val imageLoader = LocalCustomImageLoader.current
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(url) {
        val bitmap = imageLoader.getImage(url)?.asImageBitmap()
        if (bitmap == null) isError = true
        imageBitmap = bitmap
    }

    when (val bitmap = imageBitmap) {
        null -> with(LocalDensity.current) {
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center
            ) {
                if (isError) {
                    Image(imageVector = Icons.Default.Close, contentDescription = "Loading error")
                } else {
                    CircularProgressIndicator()
                }
            }
        }

        else -> {
            Image(
                modifier = modifier,
                bitmap = bitmap,
                alignment = alignment,
                contentScale = contentScale,
                contentDescription = contentDescription
            )
        }
    }
}