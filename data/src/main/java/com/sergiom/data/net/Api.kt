package com.sergiom.data.net

import com.sergiom.data.net.response.CharacterListEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Api {
    @Headers( "Accept: application/json",
        "Content-type:application/json")
    @GET("character")
    suspend fun getCharacters(): Response<CharacterListEntity>

    @Headers( "Accept: application/json",
        "Content-type:application/json")
    @GET("character/")
    suspend fun getSearchedCharacters(@Query("name") query: String):Response<CharacterListEntity>

    @Headers( "Accept: application/json",
        "Content-type:application/json")
    @GET("character/")
    suspend fun getSearchedCharactersNextPage(@Query("page") page: Int, @Query("name") query: String):Response<CharacterListEntity>
}