package com.example.testpockemonapp.ui.customimageloader

import androidx.compose.runtime.compositionLocalOf

val LocalCustomImageLoader = compositionLocalOf<CustomImageLoader> { CustomImageLoaderDefaultImpl() }