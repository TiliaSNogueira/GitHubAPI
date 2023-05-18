package com.example.github.model

data class UserRepositoriesModel(
    val id: Int?,
    val repoFullName: String?,
    val private: Boolean?,
    val ownerLogin: String?,
    val description: String?,
    val repoUrl: String?,
)