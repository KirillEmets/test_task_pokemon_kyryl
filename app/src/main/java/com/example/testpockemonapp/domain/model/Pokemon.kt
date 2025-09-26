package com.example.testpockemonapp.domain.model

data class Pokemon(
    val id: Int,
    val name: String,
    val heightCm: Int,
    val weightKg: Int,
    val imageUrl: String?
)
