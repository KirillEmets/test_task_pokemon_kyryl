package com.example.testpockemonapp.ui.screens.pokemon_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testpockemonapp.data.model.Pokemon
import com.example.testpockemonapp.data.model.Sprites
import org.w3c.dom.Text

@Composable
fun PokemonDetailsScreen(
    onBackClick: () -> Unit,
) {
    val viewModel: PokemonDetailsViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    PokemonDetailsContent(
        state = state,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PokemonDetailsContent(
    state: PokemonDetailsState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(state.name) },
                navigationIcon = {
                    IconButton(onBackClick) {
                        Icon(
                            contentDescription = null,
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack
                        )
                    }
                })
        },
        content = {
            Box(modifier = modifier.padding(it)) {
                when (state.pokemonState) {
                    PokemonDetailsRequestState.Error -> Text("Error :(")
                    PokemonDetailsRequestState.Loading -> CircularProgressIndicator(
                        modifier = Modifier.align(
                            Alignment.Center
                        )
                    )

                    is PokemonDetailsRequestState.Success -> {
                        val pokemon = state.pokemonState.pokemon
                        Column {
                            Text(
                                text = "Details"
                            )
                            Text(
                                text = """
                                    name: ${pokemon.name};
                                    height: ${pokemon.height};
                                    weight: ${pokemon.weight};
                                    sprites: ${pokemon.sprites}
                                """.trimIndent()
                            )
                        }
                    }
                }
            }
        }
    )
}

@Preview
@Composable
private fun PreviewPokemonDetailsScreen() {
    MaterialTheme {
        PokemonDetailsContent(
            state = PokemonDetailsState(
                name = "Pikachu",
                pokemonState = PokemonDetailsRequestState.Success(
                    Pokemon(
                        id = 10,
                        name = "pikachu",
                        height = 180,
                        weight = 180,
                        sprites = Sprites(null)
                    )
                ),
            ),
            onBackClick = {}
        )
    }
}