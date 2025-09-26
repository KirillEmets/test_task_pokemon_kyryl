package com.example.testpockemonapp.ui.screens.pokemon_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.testpockemonapp.domain.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val repository: PokemonRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val args = savedStateHandle.toRoute<PokemonDetailsRoute>()

    private val pokemonState: MutableStateFlow<PokemonDetailsRequestState> =
        MutableStateFlow(PokemonDetailsRequestState.Loading)

    val state = combine(pokemonState, repository.getFavorites()) { pokemonState, favorites ->
        PokemonDetailsState(
            name = args.name,
            isInFavorites = favorites.contains(args.name),
            pokemonState = pokemonState
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = PokemonDetailsState(
            name = args.name,
            isInFavorites = false,
            pokemonState = pokemonState.value
        )
    )

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            pokemonState.value = PokemonDetailsRequestState.Loading
            val result = repository.getPokemonDetails(args.name)

            pokemonState.value = when (val pokemon = result.getOrNull()) {
                null -> PokemonDetailsRequestState.Error
                else -> PokemonDetailsRequestState.Success(pokemon)
            }
        }
    }

    fun toggleFavorites() {
        viewModelScope.launch {
            when {
                state.value.isInFavorites -> repository.removeFromFavorites(args.name)
                else -> repository.addToFavorites(args.name)
            }
        }
    }
}