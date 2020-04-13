package com.amrabed.skeleton.core

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * This is the core of your application, e.g. the menu item in a menu app.
 * Update parameters of the constructor to match your own item fields.
 */
@Parcelize
data class Item(
    val id: String? = UUID.randomUUID().toString(),
    var title: String? = null,
    var summary: String? = null,
    var category: String? = null,
    var image: String? = null
) :
    Parcelable