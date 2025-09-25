 package com.example.testpockemonapp.ui.screens.pokemon_details 

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PokemonDetailsScreen(
    onAction: (PokemonDetailsAction) -> Unit
) {
    val viewModel: PokemonDetailsViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    PokemonDetailsContent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PokemonDetailsContent(
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("PokemonDetails)") })
        },
        content = {
            Box(modifier = modifier.padding(it)) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Hello PokemonDetails"
                )
            }
        }
    )
}

@Preview
@Composable
private fun PreviewPokemonDetailsScreen() {
    MaterialTheme {
        PokemonDetailsContent()
    }
}