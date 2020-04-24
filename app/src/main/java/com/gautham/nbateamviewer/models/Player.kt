package com.gautham.nbateamviewer.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Player(
    val first_name: String,
    val last_name: String,
    val position: String,
    val number: Int
) : Parcelable