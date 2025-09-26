package com.example.testpockemonapp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.testpockemonapp.data.api.PokemonApiService
import com.example.testpockemonapp.data.model.PokemonListResponse
import com.example.testpockemonapp.data.preferences.KEY_FAVORITES
import com.example.testpockemonapp.domain.model.Pokemon
import com.example.testpockemonapp.domain.model.PokemonListItem
import com.example.testpockemonapp.domain.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

const val PAGE_SIZE = 10


class PokemonRepositoryImpl @Inject constructor(
    private val api: PokemonApiService,
    private val datastore: DataStore<Preferences>
) : PokemonRepository {
    override suspend fun getPokemonDetails(name: String): Result<Pokemon> {
        return withContext(Dispatchers.IO) {
            runCatching {
                api.getPokemonDetails(name).toDomain()
            }
        }
    }

    override fun getFavorites(): Flow<Set<String>> {
        return datastore.data.map {
            it[KEY_FAVORITES] ?: emptySet()
        }
    }

    override suspend fun addToFavorites(name: String) {
        datastore.edit {
            val currentSet = it[KEY_FAVORITES] ?: emptySet()
            it[KEY_FAVORITES] = currentSet + name
        }
    }

    override suspend fun removeFromFavorites(name: String) {
        datastore.edit {
            val currentSet = it[KEY_FAVORITES] ?: emptySet()
            it[KEY_FAVORITES] = currentSet - name
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

    private val pagingSource = object : PagingSource<String, PokemonListItem>() {
        override fun getRefreshKey(state: PagingState<String, PokemonListItem>): String? {
            return "0"
        }

        override suspend fun load(params: LoadParams<String>): LoadResult<String, PokemonListItem> {
            // If params.key is null, it is the first load, so we start loading with STARTING_KEY
            val key = params.key?.toIntOrNull() ?: 0

            val result = getPokemonList(key, params.loadSize)

            val response = result.getOrNull() ?: return LoadResult.Error(
                result.exceptionOrNull() ?: Throwable("Couldn't load pokemon list")
            )

            val nextId = if (response.next != null) response.results.lastOrNull()?.getId() else null

            return LoadResult.Page(
                data = response.results.map { it.toDomain() },
                prevKey = params.key,
                nextKey = nextId.toString()
            )
        }
    }

    override val pokemonListPager: Pager<String, PokemonListItem> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = { pagingSource }
    )
}