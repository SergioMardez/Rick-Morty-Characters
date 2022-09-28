package com.sergiom.data.mappers

import com.sergiom.data.models.*
import javax.inject.Inject

class CharacterDataBaseMapper @Inject constructor(
    private val mapperOrigin: @JvmSuppressWildcards Mapper<OriginModel, OriginDataBaseModel>,
    private val mapperLocation: @JvmSuppressWildcards Mapper<LocationModel, LocationDataBaseModel>
): Mapper<CharacterModel, CharacterDataBaseModel> {
    override fun map(input: CharacterModel): CharacterDataBaseModel =
        CharacterDataBaseModel(
            id = input.id,
            name = input.name,
            status = input.status,
            species = input.species,
            type = input.type,
            gender = input.gender,
            origin = mapperOrigin.map(input.origin),
            location = mapperLocation.map(input.location),
            image = input.image,
            url = input.url,
            created = input.created
        )
}

class OriginDataBaseMapper @Inject constructor(): Mapper<OriginModel, OriginDataBaseModel> {
    override fun map(input: OriginModel): OriginDataBaseModel =
        OriginDataBaseModel(
            name = input.name
        )
}

class LocationDataBaseMapper @Inject constructor(): Mapper<LocationModel, LocationDataBaseModel> {
    override fun map(input: LocationModel): LocationDataBaseModel =
        LocationDataBaseModel(
            name = input.name
        )
}