package com.example.testpockemonapp.ui.screens.pokemon_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.testpockemonapp.domain.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _event = Channel<PokemonListEvent>()
    val event = _event.receiveAsFlow()

    val pagingFlow = repository.pokemonListPager.flow.cachedIn(viewModelScope)

    fun handleIntent(intent: PokemonListIntent) {
        when (intent) {
            else -> Unit
        }
    }
}