package com.sergiom.data.mappers

import com.sergiom.data.models.*
import com.sergiom.data.net.response.*
import javax.inject.Inject

class CharacterListMapper @Inject constructor(
    private val mapperInfo: @JvmSuppressWildcards Mapper<InfoEntity, InfoModel>,
    private val mapperCharacter: @JvmSuppressWildcards Mapper<CharacterEntity, CharacterModel>
): Mapper<CharacterListEntity, CharacterListModel> {
    override fun map(input: CharacterListEntity): CharacterListModel =
        CharacterListModel(
            info = input.info?.let { mapperInfo.map(it) } ?: InfoModel(),
            results = input.results?.map {
                mapperCharacter.map(it)
            } ?: listOf()
        )
}

class InfoMapper @Inject constructor(): Mapper<InfoEntity, InfoModel> {
    override fun map(input: InfoEntity): InfoModel =
        InfoModel(
            count = input.count ?: 0,
            pages = input.pages ?: 0,
            next = input.next ?: "",
            prev = input.prev ?: ""
        )
}

class CharacterMapper @Inject constructor(
    private val mapperOrigin: @JvmSuppressWildcards Mapper<OriginEntity, OriginModel>,
    private val mapperLocation: @JvmSuppressWildcards Mapper<LocationEntity, LocationModel>
): Mapper<CharacterEntity, CharacterModel> {
    override fun map(input: CharacterEntity): CharacterModel =
        CharacterModel(
            id = input.id ?: 0,
            name = input.name ?: "",
            status = input.status ?: "",
            species = input.species ?: "",
            type = input.type ?: "",
            gender = input.gender ?: "",
            origin = input.origin?.let { mapperOrigin.map(it) } ?: OriginModel(),
            location = input.location?.let { mapperLocation.map(it) } ?: LocationModel(),
            image = input.image ?: "",
            url = input.url ?: "",
            created = input.created ?: ""
        )
}

class OriginMapper @Inject constructor(): Mapper<OriginEntity, OriginModel> {
    override fun map(input: OriginEntity): OriginModel =
        OriginModel(
            name = input.name ?: ""
        )
}

class LocationMapper @Inject constructor(): Mapper<LocationEntity, LocationModel> {
    override fun map(input: LocationEntity): LocationModel =
        LocationModel(
            name = input.name ?: ""
        )
}