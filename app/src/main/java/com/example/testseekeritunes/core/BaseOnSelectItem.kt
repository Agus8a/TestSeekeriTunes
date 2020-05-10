package com.example.testseekeritunes.core

/**
 * Base interface for item selection featured in lists and recycler views.
 */
interface BaseOnSelectItem<T> {
    fun onSelectItem(item: T)
}