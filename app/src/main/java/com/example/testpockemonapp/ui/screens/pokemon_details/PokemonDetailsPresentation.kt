package com.example.testpockemonapp.ui.screens.pokemon_details

import com.example.testpockemonapp.data.model.Pokemon

sealed interface PokemonDetailsIntent

data class PokemonDetailsState(
    val name: String,
    val pokemonState: PokemonDetailsRequestState
)

sealed interface PokemonDetailsRequestState {
    data class Success(val pokemon: Pokemon): PokemonDetailsRequestState
    data object Loading: PokemonDetailsRequestState
    data object Error: PokemonDetailsRequestState
}
