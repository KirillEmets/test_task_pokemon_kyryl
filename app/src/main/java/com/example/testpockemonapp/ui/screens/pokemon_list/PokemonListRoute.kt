package com.example.testpockemonapp.ui.screens.pokemon_list

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object PokemonListRoute

internal fun NavGraphBuilder.pokemonList(
    onPokemonItemClicked: (name: String) -> Unit,
) {
    composable<PokemonListRoute> { backStackEntry ->
        PokemonListScreen(onPokemonItemClicked = onPokemonItemClicked)
    }
}