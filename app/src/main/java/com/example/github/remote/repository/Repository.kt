package com.example.github.remote.repository

import com.example.github.network.ApiResult
import com.example.github.remote.responses.UserRepositoriesResponse
import com.example.github.remote.responses.UserResponse
import com.example.github.remote.responses.UserSearchListResponse
import com.example.github.remote.responses.UsersListResponse

interface Repository {

    suspend fun getAllUsers(): ApiResult<List<UsersListResponse>>

    suspend fun getSearchUser(userName: String): ApiResult<UserSearchListResponse>

    suspend fun getUser(userName: String): ApiResult<UserResponse>

    suspend fun getUserRepositories(userName: String): ApiResult<List<UserRepositoriesResponse>>

}