package org.wit.hillforts.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity
data class HillfortModel(@PrimaryKey(autoGenerate = true)
            var fbId : String = "",
            var id: Long = 0,
            var title: String = "",
            var description: String = "",
            var image1: String = " ",
            var image2: String = " ",
            var lat: Double = 0.0,
            var lng: Double = 0.0,
            var zoom: Float = 0f,
            var visited: Boolean = false,
            var rating: Float = 0f,
            var isFavorite: Boolean = false) : Parcelable

@Parcelize
data class Location(
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f) : Parcelable

