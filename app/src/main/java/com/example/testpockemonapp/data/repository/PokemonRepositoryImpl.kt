package com.example.testpockemonapp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.example.testpockemonapp.data.api.PokemonApiService
import com.example.testpockemonapp.data.model.PokemonListItemResponse
import com.example.testpockemonapp.data.model.PokemonListResponse
import com.example.testpockemonapp.data.preferences.KEY_DELETED
import com.example.testpockemonapp.data.preferences.KEY_FAVORITES
import com.example.testpockemonapp.domain.model.Pokemon
import com.example.testpockemonapp.domain.model.PokemonListItem
import com.example.testpockemonapp.domain.repository.PokemonRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

const val PAGE_SIZE = 10


class PokemonRepositoryImpl @Inject constructor(
    private val api: PokemonApiService,
    private val datastore: DataStore<Preferences>,
) : PokemonRepository {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun getPokemonDetails(name: String): Flow<Pokemon> = flow {
        val pokemonDetails = withContext(Dispatchers.IO) {
            api.getPokemonDetails(name)
        }

        val resultFlow = datastore.data.map { preferences ->
            val favorites = preferences[KEY_FAVORITES].orEmpty()
            val isInFavorites = favorites.contains(name)
            pokemonDetails.toDomain(isInFavorites)
        }

        emitAll(resultFlow)
    }

    override suspend fun toggleIsInFavorites(name: String) {
        datastore.edit {
            val currentSet = it[KEY_FAVORITES].orEmpty()
            it[KEY_FAVORITES] = when {
                currentSet.contains(name) -> currentSet - name
                else -> currentSet + name
            }
        }
    }

    override suspend fun deletePokemon(name: String) {
        datastore.edit {
            val currentSet = it[KEY_DELETED].orEmpty()
            it[KEY_DELETED] = currentSet + name
        }
    }

    private suspend fun getPokemonList(
        offset: Int,
        limit: Int,
    ): Result<PokemonListResponse> {
        return withContext(Dispatchers.IO) {
            runCatching {
                api.getPokemonList(offset, limit)
            }
        }
    }

    private val pagingSource = object : PagingSource<String, PokemonListItemResponse>() {
        override fun getRefreshKey(state: PagingState<String, PokemonListItemResponse>): String? {
            return "0"
        }

        override suspend fun load(params: LoadParams<String>): LoadResult<String, PokemonListItemResponse> {
            // If params.key is null, it is the first load, so we start loading with STARTING_KEY
            val key = params.key?.toIntOrNull() ?: 0

            val result = getPokemonList(key, params.loadSize)

            val response = result.getOrNull() ?: return LoadResult.Error(
                result.exceptionOrNull() ?: Throwable("Couldn't load pokemon list")
            )

            val nextId = if (response.next != null) response.results.lastOrNull()?.getId() else null

            return LoadResult.Page(
                data = response.results,
                prevKey = params.key,
                nextKey = nextId.toString()
            )
        }
    }

    private val pager = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = { pagingSource }
    )

    override val pokemonListFlow: Flow<PagingData<PokemonListItem>> = combine(
        pager.flow.cachedIn(coroutineScope),
        datastore.data
    ) { pagingData, preferences ->
        val favorites = preferences[KEY_FAVORITES].orEmpty()
        val deleted = preferences[KEY_DELETED].orEmpty()

        pagingData
            .filter { !deleted.contains(it.name) }
            .map { item ->
                PokemonListItem(
                    name = item.name,
                    url = item.url,
                    isInFavorites = favorites.contains(item.name),
                )
            }
    }
}