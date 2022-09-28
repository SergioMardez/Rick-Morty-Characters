package com.sergiom.data.net.response

data class CharacterListEntity(
    val info: InfoEntity?,
    val results: List<CharacterEntity>?
)

data class InfoEntity(
    val count: Int?,
    val pages: Int?,
    val next: String?,
    val prev: String?
)

data class CharacterEntity(
    val id: Int?,
    val name: String?,
    val status: String?,
    val species: String?,
    val type: String?,
    val gender: String?,
    val origin: OriginEntity?,
    val location: LocationEntity?,
    val image: String?,
    val url: String?,
    val created: String?
)

data class OriginEntity(
    val name: String?
)

data class LocationEntity(
    val name: String?
)