package com.example.testpockemonapp.data.api

import com.example.testpockemonapp.data.model.PokemonResponse
import com.example.testpockemonapp.data.model.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {

    @GET("v2/pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): PokemonListResponse

    @GET("v2/pokemon/{name}")
    suspend fun getPokemonDetails(
        @Path("name") name: String
    ): PokemonResponse
}