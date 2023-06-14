package com.example.codingchallengeimgur.ui.common.extension

import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.example.codingchallengeimgur.R
import com.example.codingchallengeimgur.domain.Resource

fun FragmentActivity.observeNetworkCall(resource: Resource<*>) {
    val loader: View? = findViewById(R.id.loader)
    loader?.isVisible = resource is Resource.Loading
}
