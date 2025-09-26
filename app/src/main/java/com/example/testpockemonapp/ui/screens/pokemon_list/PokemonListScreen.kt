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
import androidx.compose.material.icons.filled.FavoriteBorder
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
import com.example.testpockemonapp.domain.model.PokemonListItem
import kotlinx.coroutines.flow.flowOf
import java.util.UUID

@Composable
fun PokemonListScreen(
    onPokemonItemClicked: (name: String) -> Unit,
) {
    val viewModel: PokemonListViewModel = hiltViewModel()
    val lazyPagingItems = viewModel.pagingFlow.collectAsLazyPagingItems()

    PokemonListContent(
        modifier = Modifier,
        lazyPagingItems = lazyPagingItems,
        onItemClick = onPokemonItemClicked,
        onFavoriteIconClick = viewModel::toggleFavorites,
        onDeleteIconClick = viewModel::deletePokemon
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PokemonListContent(
    lazyPagingItems: LazyPagingItems<PokemonListItem>,
    onItemClick: (name: String) -> Unit,
    onFavoriteIconClick: (name: String) -> Unit,
    onDeleteIconClick: (name: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Pokemon List") })
        },
        content = {
            Box(modifier = modifier.padding(it)) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(
                        count = lazyPagingItems.itemCount,
                        key = { index ->
                            val item = lazyPagingItems[index]
                            item?.name.orEmpty()
                        },
                        itemContent = { index ->
                            val item = lazyPagingItems[index]

                            if (item != null) {
                                PokemonListItemCard(
                                    modifier = Modifier.animateItem(),
                                    pokemon = item,
                                    onItemClick = { onItemClick(item.name) },
                                    onFavoriteIconClick = { onFavoriteIconClick(item.name) },
                                    onDeleteIconClick = { onDeleteIconClick(item.name) }
                                )
                            }
                        }
                    )
                }
            }
        }
    )
}

@Composable
private fun PokemonListItemCard(
    pokemon: PokemonListItem,
    onItemClick: () -> Unit,
    onFavoriteIconClick: () -> Unit,
    onDeleteIconClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        onClick = onItemClick
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
            IconButton(onClick = onFavoriteIconClick) {
                val icon = when {
                    pokemon.isInFavorites -> Icons.Default.Favorite
                    else -> Icons.Default.FavoriteBorder
                }

                Icon(imageVector = icon, contentDescription = null)
            }

            IconButton(onClick = onDeleteIconClick) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
            }
        }
    }
}

private val mockPokemonData = flowOf(
    PagingData.from(
        listOf(
            PokemonListItem(
                name = "Pikachu",
                url = "url1",
                isInFavorites = true
            ),
            PokemonListItem(
                name = "Bulbasaur",
                url = "url1",
                isInFavorites = false
            ),
        )
    )
)

@Preview
@Composable
private fun PreviewPokemonListScreen() {
    MaterialTheme {
        val items = mockPokemonData.collectAsLazyPagingItems()
        PokemonListContent(
            items, onItemClick = {},
            onFavoriteIconClick = {},
            onDeleteIconClick = {},
        )
    }
}