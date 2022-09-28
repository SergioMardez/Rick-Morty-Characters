package com.sergiom.data.models

data class CharacterListModel(
    val info: InfoModel,
    val results: List<CharacterModel> = listOf()
)

data class InfoModel(
    val count: Int = 0,
    val pages: Int = 0,
    val next: String = "",
    val prev: String = ""
)

data class CharacterModel(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: OriginModel,
    val location: LocationModel,
    val image: String,
    val url: String,
    val created: String
)

data class OriginModel(
    val name: String = ""
)

data class LocationModel(
    val name: String = ""
)