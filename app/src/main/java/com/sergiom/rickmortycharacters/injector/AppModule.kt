package com.sergiom.rickmortycharacters.injector

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sergiom.data.datasource.CharacterDataSource
import com.sergiom.data.local.AppDatabase
import com.sergiom.data.local.CharactersDao
import com.sergiom.data.mappers.*
import com.sergiom.data.models.*
import com.sergiom.data.net.Api
import com.sergiom.data.net.RestClient
import com.sergiom.data.net.RestClientImpl
import com.sergiom.data.net.response.*
import com.sergiom.data.repository.CharacterRepository
import com.sergiom.data.repository.NetRepository
import com.sergiom.data.repositoryimpl.NetRepositoryImpl
import com.sergiom.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    /*
    * NETWORK
    * */
    @Singleton
    @Provides
    fun provideRemoteCaller(): Api = RestClientImpl().getRemoteCaller()

    @Provides
    fun provideRestClient(): RestClient = RestClientImpl()

    @Provides
    fun provideNetRepository(restClient: RestClient, dataSource: CharacterDataSource,
                             mapper: CharacterListMapper, mapperCharacter: CharacterMapper):
            NetRepository = NetRepositoryImpl(restClient, dataSource, mapper, mapperCharacter)

    @Provides
    fun providesDataSource(restClient: RestClient): CharacterDataSource = CharacterDataSource(restClient)

    /*
    * DATA BASE
    * */
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideEventsDao(db: AppDatabase) = db.eventsDao()

    @Singleton
    @Provides
    fun provideBeerRepository(localDataSource: CharactersDao, mapper: CharacterDataBaseMapper) = CharacterRepository(localDataSource, mapper)

    /*
    * MAPPERS
    * */
    @Provides
    fun bindsCharacterListMapper(infoMapper: InfoMapper, characterMapper: CharacterMapper):
            Mapper<CharacterListEntity, CharacterListModel> = CharacterListMapper(infoMapper, characterMapper)

    @Provides
    fun bindsInfoMapper(): Mapper<InfoEntity, InfoModel> = InfoMapper()

    @Provides
    fun bindsCharacterMapper(originMapper: OriginMapper, locationMapper: LocationMapper):
            Mapper<CharacterEntity, CharacterModel> = CharacterMapper(originMapper, locationMapper)

    @Provides
    fun bindsOriginMapper(): Mapper<OriginEntity, OriginModel> = OriginMapper()

    @Provides
    fun bindsLocationMapper(): Mapper<LocationEntity, LocationModel> = LocationMapper()

    @Provides
    fun bindsCharacterDataBaseMapper(originMapper: OriginDataBaseMapper, locationMapper: LocationDataBaseMapper):
            Mapper<CharacterModel, CharacterDataBaseModel> = CharacterDataBaseMapper(originMapper, locationMapper)

    @Provides
    fun bindsOriginDataBaseMapper(): Mapper<OriginModel, OriginDataBaseModel> = OriginDataBaseMapper()

    @Provides
    fun bindsLocationDataBaseMapper(): Mapper<LocationModel, LocationDataBaseModel> = LocationDataBaseMapper()

    /*
    * NETWORK USE CASES
    * */
    @Provides
    fun providesGetCharactersUseCase(netRepository: NetRepository): GetCharactersUseCase = GetCharactersUseCaseImpl(netRepository)

    @Provides
    fun providesGetPaginationCharactersUseCase(netRepository: NetRepository): GetPaginationCharactersUseCase = GetPaginationCharactersUseCaseImpl(netRepository)

    /*
    * DATA BASE USE CASES
    * */
    @Provides
    fun providesDeleteAllCharacterUseCase(characterRepository: CharacterRepository): DeleteAllCharacterUseCase = DeleteAllCharacterUseCaseImpl(characterRepository)

    @Provides
    fun providesGetCharacterFromDataBaseUseCase(characterRepository: CharacterRepository):
            GetCharacterFromDataBaseUseCase = GetCharacterFromDataBaseUseCaseImpl(characterRepository)

    @Provides
    fun providesGetCharactersFromDataBaseUseCase(characterRepository: CharacterRepository):
            GetCharactersFromDataBaseUseCase = GetCharactersFromDataBaseUseCaseImpl(characterRepository)

    @Provides
    fun providesSaveCharacterToDataBaseUseCase(characterRepository: CharacterRepository):
            SaveCharacterToDataBaseUseCase = SaveCharacterToDataBaseUseCaseImpl(characterRepository)
}