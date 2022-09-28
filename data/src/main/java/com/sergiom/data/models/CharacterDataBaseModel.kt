package com.sergiom.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterDataBaseModel(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: OriginDataBaseModel,
    val location: LocationDataBaseModel,
    val image: String,
    val url: String,
    val created: String
)

data class OriginDataBaseModel(
    val name: String = ""
)

data class LocationDataBaseModel(
    val name: String = ""
)
