package com.example.testpockemonapp.domain.repository

import androidx.paging.Pager
import com.example.testpockemonapp.domain.model.PokemonListItem
import com.example.testpockemonapp.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    val pokemonListPager: Pager<String, PokemonListItem>

    suspend fun getPokemonDetails(name: String): Result<Pokemon>

    fun getFavorites(): Flow<Set<String>>
    suspend fun addToFavorites(name: String)
    suspend fun removeFromFavorites(name: String)
}
