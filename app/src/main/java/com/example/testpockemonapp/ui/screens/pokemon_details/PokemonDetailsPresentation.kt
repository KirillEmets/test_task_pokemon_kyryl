package com.example.testpockemonapp.ui.screens.pokemon_details

import com.example.testpockemonapp.data.model.Pokemon

data class PokemonDetailsState(
    val name: String,
    val isInFavorites: Boolean,
    val pokemonState: PokemonDetailsRequestState
)

sealed interface PokemonDetailsRequestState {
    data class Success(val pokemon: Pokemon): PokemonDetailsRequestState
    data object Loading: PokemonDetailsRequestState
    data object Error: PokemonDetailsRequestState
}
