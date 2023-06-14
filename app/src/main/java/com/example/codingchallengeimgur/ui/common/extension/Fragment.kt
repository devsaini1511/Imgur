package com.example.codingchallengeimgur.ui.common.extension

import android.graphics.Color
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("networkAvailable")
fun TextView.networkAvailableBackground(isAvailable: Boolean) {
    setTextColor(Color.WHITE)
    setBackgroundColor(if (!isAvailable) Color.BLACK else Color.parseColor("#125D98"))
    text = if (isAvailable) "Back online" else "Network not available"
}


