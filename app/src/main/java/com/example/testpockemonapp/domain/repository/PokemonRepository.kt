package com.example.testpockemonapp.domain.repository

import androidx.paging.PagingData
import com.example.testpockemonapp.domain.model.PokemonListItem
import com.example.testpockemonapp.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    val pokemonListFlow: Flow<PagingData<PokemonListItem>>

    fun getPokemonDetails(name: String): Flow<Pokemon>

    suspend fun toggleIsInFavorites(name: String)
    suspend fun deletePokemon(name: String)
}
