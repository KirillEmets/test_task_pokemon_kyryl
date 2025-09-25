 package com.example.testpockemonapp.ui.screens.pokemon_details 
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object PokemonDetailsRoute

internal fun NavController.navigateToPokemonDetails() = navigate(route = PokemonDetailsRoute)

internal fun NavGraphBuilder.pokemonDetails() {
    composable<PokemonDetailsRoute> { backStackEntry ->
        PokemonDetailsScreen(
            onAction = {
                when (it) {
                    else -> Unit
                }
            }
        )
    }
}