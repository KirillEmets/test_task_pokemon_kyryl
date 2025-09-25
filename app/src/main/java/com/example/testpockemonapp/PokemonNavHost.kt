package com.example.testpockemonapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.testpockemonapp.ui.screens.pokemon_details.navigateToPokemonDetails
import com.example.testpockemonapp.ui.screens.pokemon_details.pokemonDetails
import com.example.testpockemonapp.ui.screens.pokemon_list.PokemonListRoute
import com.example.testpockemonapp.ui.screens.pokemon_list.pokemonList

@Composable
fun PokemonNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = PokemonListRoute
    ) {
        pokemonList(onPokemonItemClicked = navController::navigateToPokemonDetails)
        pokemonDetails(onBack = { navController.navigateUp() })
    }
}