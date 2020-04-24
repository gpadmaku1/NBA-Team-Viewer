package com.gautham.nbateamviewer.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Team(
    var wins: Int,
    var losses: Int,
    var full_name: String,
    var players: List<Player>
) : Parcelable