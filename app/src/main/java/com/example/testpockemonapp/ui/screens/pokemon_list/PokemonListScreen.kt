package com.example.testpockemonapp.ui.screens.pokemon_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.testpockemonapp.data.model.PokemonBasic
import kotlinx.coroutines.flow.flowOf

@Composable
fun PokemonListScreen(
    onAction: (PokemonListAction) -> Unit,
) {
    val viewModel: PokemonListViewModel = hiltViewModel()
    val lazyPagingItems = viewModel.pagingFlow.collectAsLazyPagingItems()


    PokemonListContent(
        modifier = Modifier,
        lazyPagingItems = lazyPagingItems
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PokemonListContent(
    lazyPagingItems: LazyPagingItems<PokemonBasic>,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("PokemonList") })
        },
        content = {
            Box(modifier = modifier.padding(it)) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(lazyPagingItems.itemCount) { index ->
                        val item = lazyPagingItems[index]

                        if (item != null) {
                            PokemonListItem(item)
                        }
                    }
                }
            }
        }
    )
}

@Composable
private fun PokemonListItem(
    pokemon: PokemonBasic,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = pokemon.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            IconButton(onClick = {}) {
                Icon(Icons.Default.Favorite, contentDescription = null)
            }
            IconButton(onClick = {}) {
                Icon(Icons.Default.Delete, contentDescription = null)
            }
        }
    }
}

private val mockPokemonData = flowOf(
    PagingData.from(
        listOf(
            PokemonBasic("Pikachu", "url1"),
            PokemonBasic("Bulbasaur", "url1"),
        )
    )
)

@Preview
@Composable
private fun PreviewPokemonListScreen() {
    MaterialTheme {
        val items = mockPokemonData.collectAsLazyPagingItems()
        PokemonListContent(items)
    }
}