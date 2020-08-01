package com.example.finderapp.Data

data class InfoRepository(
    val id: Int?,
    val name: String?,
    val description: String?,
    val language: String?,
    val forksCount: Int?,
    val stargazersCount: Int?,
    val ownerLogin: String?,
    val ownerAvatarUrl: String?,
    val commitsUrl: String?
)