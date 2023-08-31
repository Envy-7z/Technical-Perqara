package com.wisnu.technicalassessmentlimaperqara.utils

import android.annotation.SuppressLint
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import java.text.SimpleDateFormat
import java.util.Date

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun AppCompatTextView.setOrGone(label: String) {
    if (label.isEmpty()) {
        this.gone()
    } else {
        this.visible()
        this.text = label
    }
}

@SuppressLint("SimpleDateFormat")
fun String.simpleFormattedDate(): String {
    return try {
        val output = SimpleDateFormat("dd MMM yyyy, HH:mm:ss")
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val date: Date = sdf.parse(this)
        return output.format(date)
    } catch (e: Exception) {
        e.printStackTrace()
        this
    }
}