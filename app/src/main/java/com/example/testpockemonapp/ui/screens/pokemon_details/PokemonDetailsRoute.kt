 package com.example.testpockemonapp.ui.screens.pokemon_details 
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetailsRoute(val name: String)

internal fun NavController.navigateToPokemonDetails(
    name: String
) = navigate(route = PokemonDetailsRoute(name))

internal fun NavGraphBuilder.pokemonDetails(onBack: () -> Unit) {
    composable<PokemonDetailsRoute> { backStackEntry ->
        PokemonDetailsScreen(
            onBackClick = onBack
        )
    }
}