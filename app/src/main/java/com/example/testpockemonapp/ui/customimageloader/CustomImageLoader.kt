package com.example.testpockemonapp.ui.customimageloader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL


interface CustomImageLoader {
    suspend fun getImage(url: String): Bitmap?
}

const val MAX_CACHE_SIZE = 20
const val TAG = "CustomImageLoaderDefaultImpl"

class CustomImageLoaderDefaultImpl : CustomImageLoader {
    private val cache = linkedMapOf<String, Bitmap>()

    override suspend fun getImage(url: String): Bitmap? {
        if (cache.contains(url)) {
            return cache[url]
        }

        val bitmap = withContext(Dispatchers.IO) {
            fetchBitmap(imageUrl = url)
        }

        if (bitmap != null) {
            cache += url to bitmap
        }

        if (cache.size > MAX_CACHE_SIZE) {
            cache.keys.firstOrNull()?.let(cache::remove)
        }

        return bitmap
    }

    private fun fetchBitmap(imageUrl: String): Bitmap? {
        return try {
            val url = URL(imageUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.connect()

            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true

            connection.inputStream.use { stream ->
                BitmapFactory.decodeStream(stream, null, options)
            }

            options.inSampleSize = calculateInSampleSize(
                width = options.outWidth,
                height = options.outHeight,
                maxWidth = 1080,
                maxHeight = 1080
            )

            options.inJustDecodeBounds = false

            val secondConnection = url.openConnection() as HttpURLConnection
            secondConnection.connect()
            secondConnection.inputStream.use { stream ->
                BitmapFactory.decodeStream(stream, null, options)
            }
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
            null
        }
    }

    private fun calculateInSampleSize(
        width: Int,
        height: Int,
        maxWidth: Int,
        maxHeight: Int
    ): Int {
        var inSampleSize = 1

        if (height > maxHeight || width > maxWidth) {
            val heightRatio = height.toFloat() / maxHeight.toFloat()
            val widthRatio = width.toFloat() / maxWidth.toFloat()
            inSampleSize = maxOf(heightRatio, widthRatio).toInt()
        }

        return inSampleSize.coerceAtLeast(1)
    }
}

