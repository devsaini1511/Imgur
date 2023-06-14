package com.example.codingchallengeimgur.domain.response

import android.annotation.SuppressLint
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

data class ImageData(
    val title: String,
    val datetime: Long,
    val images: List<ImageDetails>
) {
    val imageUrl: String
        get() = images.firstOrNull()?.link ?: ""

    val formattedDate: String
        @SuppressLint("NewApi")
        get() {
            val localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(datetime), ZoneId.systemDefault())
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yy hh:mm a", Locale.getDefault())
            return localDateTime.format(formatter)
        }

    val numImages: Int
        get() = images.size - 1
}

data class ImageDetails(
    val link: String
)
