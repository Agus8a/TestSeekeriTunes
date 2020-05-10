package com.example.testseekeritunes.util

import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun Context.toast(message: String) {
    println("ERROR: $message")
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun RecyclerView?.build(context: Context) {
    val layoutManager = LinearLayoutManager(context)
    this?.hasFixedSize()
    this?.layoutManager = layoutManager
    this?.addItemDecoration(
        DividerItemDecoration(
            context,
            layoutManager.orientation
        )
    )
}