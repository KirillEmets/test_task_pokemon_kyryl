package com.example.testpockemonapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.testpockemonapp.data.repository.PokemonRepositoryImpl
import com.example.testpockemonapp.domain.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    val Context.dataStore by preferencesDataStore("pokemonDataStore")

    @Provides
    fun bindPokemonRepository(impl: PokemonRepositoryImpl): PokemonRepository {
        return impl
    }

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }
}
