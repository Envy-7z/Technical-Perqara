package com.wisnu.technicalassessmentlimaperqara.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "MainTable")
@Parcelize
data class FavEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "background_image")
    var background_image: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "released")
    var released: String,
    @ColumnInfo(name = "rating")
    var rating: Double

) : Parcelable
