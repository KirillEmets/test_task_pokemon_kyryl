package com.example.testpockemonapp.ui.screens.pokemon_list

sealed interface PokemonListIntent
sealed interface PokemonListEvent
sealed interface PokemonListAction

data class PokemonListState(
    val data: String = "",
)
