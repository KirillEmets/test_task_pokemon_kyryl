package com.example.testpockemonapp.ui.screens.pokemon_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testpockemonapp.domain.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository,
) : ViewModel() {

    val pagingFlow = repository.pokemonListFlow

    fun toggleFavorites(name: String) {
        viewModelScope.launch {
            repository.toggleIsInFavorites(name)
        }
    }

    fun deletePokemon(name: String) {
        viewModelScope.launch {
            repository.deletePokemon(name)
        }
    }
}