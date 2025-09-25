package com.example.testpockemonapp.ui.screens.pokemon_list

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object PokemonListRoute

internal fun NavController.navigateToPokemonList() = navigate(route = PokemonListRoute)

internal fun NavGraphBuilder.pokemonList() {
    composable<PokemonListRoute> { backStackEntry ->
        PokemonListScreen(
            onAction = {
                when (it) {
                    else -> Unit
                }
            }
        )
    }
}