package com.example.testpockemonapp.ui.screens.pokemon_details

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.testpockemonapp.data.model.Pokemon
import com.example.testpockemonapp.data.model.PokemonBasic
import com.example.testpockemonapp.data.repository.PokemonRepository
import com.example.testpockemonapp.data.repository.PokemonRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val repository: PokemonRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(PokemonDetailsState())
    val state = _state.asStateFlow()

    private val _event = Channel<PokemonDetailsEvent>()
    val event = _event.receiveAsFlow()

    init {

    }


    fun handleIntent(intent: PokemonDetailsIntent) {
        when (intent) {
            else -> Unit
        }
    }
}