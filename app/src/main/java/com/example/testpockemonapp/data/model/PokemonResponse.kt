package com.example.testpockemonapp.data.model

import com.example.testpockemonapp.domain.model.Pokemon
import com.example.testpockemonapp.domain.model.PokemonListItem
import com.google.gson.annotations.SerializedName

data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonListItemResponse>,
)

data class PokemonListItemResponse(
    val name: String,
    val url: String
) {
    fun getId(): Int {
        return url.split("/").dropLast(1).last().toInt()
    }

    fun toDomain(): PokemonListItem = PokemonListItem(name, url)
}

data class PokemonResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: Sprites,
) {
    fun toDomain(): Pokemon = Pokemon(
        id = id,
        name = name,
        heightCm = height * 10,
        weightKg = weight / 10,
        imageUrl = sprites.frontDefault
    )
}

data class Sprites(
    @SerializedName("front_default")
    val frontDefault: String?,
)