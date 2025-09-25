package com.example.testpockemonapp.ui.screens.pokemon_details

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testpockemonapp.data.model.Pokemon
import com.example.testpockemonapp.data.model.Sprites
import com.example.testpockemonapp.ui.customimageloader.CustomImage

@Composable
fun PokemonDetailsScreen(
    onBackClick: () -> Unit,
) {
    val viewModel: PokemonDetailsViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    PokemonDetailsContent(
        state = state,
        onBackClick = onBackClick,
        onFavoriteButtonClick = viewModel::toggleFavorites
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PokemonDetailsContent(
    state: PokemonDetailsState,
    onBackClick: () -> Unit,
    onFavoriteButtonClick: () -> Unit,
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
        content = { paddingValues ->
            Box(modifier = modifier.padding(paddingValues)) {
                when (state.pokemonState) {
                    PokemonDetailsRequestState.Error -> Text("Error :(")
                    PokemonDetailsRequestState.Loading -> CircularProgressIndicator(
                        modifier = Modifier.align(
                            Alignment.Center
                        )
                    )

                    is PokemonDetailsRequestState.Success -> {
                        val pokemon = state.pokemonState.pokemon
                        Card(modifier = Modifier.padding(16.dp)) {
                            Row(modifier = Modifier.padding(8.dp)) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        style = MaterialTheme.typography.headlineSmall,
                                        text = "Details"
                                    )

                                    Row {
                                        Text("Name: ")
                                        Text(pokemon.name, fontWeight = FontWeight.Bold)
                                    }

                                    Row {
                                        Text("Height: ")
                                        Text(
                                            "${pokemon.height * 10}cm",
                                            fontWeight = FontWeight.Bold
                                        )
                                    }

                                    Row {
                                        Text("Weight: ")
                                        Text(
                                            "${pokemon.weight / 10}kg",
                                            fontWeight = FontWeight.Bold
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))
                                }

                                val icon = when {
                                    state.isInFavorites -> Icons.Default.Favorite
                                    else -> Icons.Default.FavoriteBorder
                                }

                                IconButton(onClick = onFavoriteButtonClick) {
                                    Icon(imageVector = icon, contentDescription = "Favorite")
                                }
                            }

                            // Spinning is just for fun
                            var spinAnimationTarget by remember { mutableFloatStateOf(0f) }
                            val spinAnimation by animateFloatAsState(spinAnimationTarget)

                            pokemon.sprites.frontDefault?.let { url ->
                                CustomImage(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1f)
                                        .graphicsLayer {
                                            rotationZ = spinAnimation
                                        }
                                        .clickable(
                                            onClick = {
                                                spinAnimationTarget += 360f
                                            },
                                            interactionSource = null,
                                            indication = null
                                        ),
                                    url = url,
                                    contentDescription = pokemon.name
                                )
                            }
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
                        height = 18,
                        weight = 1000,
                        sprites = Sprites("")
                    )
                ),
                isInFavorites = true
            ),
            onBackClick = {},
            onFavoriteButtonClick = {}
        )
    }
}