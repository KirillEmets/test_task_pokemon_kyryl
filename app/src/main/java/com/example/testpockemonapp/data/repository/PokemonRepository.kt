package com.example.testpockemonapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.testpockemonapp.data.api.PokemonApiService
import com.example.testpockemonapp.data.model.Pokemon
import com.example.testpockemonapp.data.model.PokemonBasic
import com.example.testpockemonapp.data.model.PokemonListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

const val PAGE_SIZE = 10

interface PokemonRepository {
    val pokemonListPager: Pager<String, PokemonBasic>

    suspend fun getPokemonList(
        offset: Int,
        limit: Int,
    ): Result<PokemonListResponse>

    suspend fun getPokemonDetails(
        name: String,
    ): Result<Pokemon>
}

class PokemonRepositoryImpl @Inject constructor(
    private val api: PokemonApiService,
) : PokemonRepository {
    override suspend fun getPokemonList(
        offset: Int,
        limit: Int,
    ): Result<PokemonListResponse> {
        return withContext(Dispatchers.IO) {
            runCatching {
                api.getPokemonList(offset, limit)
            }
        }
    }

    override suspend fun getPokemonDetails(name: String): Result<Pokemon> {
        return withContext(Dispatchers.IO) {
            runCatching {
                api.getPokemonDetails(name)
            }
        }
    }

    override val pokemonListPager = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = { MyPagingSource(this) }
    )
}

private class MyPagingSource(private val repository: PokemonRepository) : PagingSource<String, PokemonBasic>() {
    override fun getRefreshKey(state: PagingState<String, PokemonBasic>): String? {
        return "0"
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, PokemonBasic> {
        // If params.key is null, it is the first load, so we start loading with STARTING_KEY
        val key = params.key?.toIntOrNull() ?: 0

        val result = repository.getPokemonList(key, params.loadSize)

        val response = result.getOrNull() ?: return LoadResult.Error(result.exceptionOrNull() ?: Throwable("Couldn't load pokemon list"))

        val nextId = if (response.next != null) response.results.lastOrNull()?.getId() else null

        return LoadResult.Page(
            data = response.results,
            prevKey = params.key,
            nextKey = nextId.toString()
        )
    }
}