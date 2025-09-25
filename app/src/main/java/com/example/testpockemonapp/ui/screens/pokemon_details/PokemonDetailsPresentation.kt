package com.example.testpockemonapp.ui.screens.pokemon_details

sealed interface PokemonDetailsIntent
sealed interface PokemonDetailsEvent
sealed interface PokemonDetailsAction

data class PokemonDetailsState(
    val data: String = "",
)
