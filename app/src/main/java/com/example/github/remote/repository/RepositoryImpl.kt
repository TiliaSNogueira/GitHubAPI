package com.example.github.remote.repository

import com.example.github.network.ApiResult
import com.example.github.network.getResult
import com.example.github.remote.api.GitHubAPI
import com.example.github.remote.responses.UserRepositoriesResponse
import com.example.github.remote.responses.UserResponse
import com.example.github.remote.responses.UserSearchListResponse
import com.example.github.remote.responses.UsersListResponse

class RepositoryImpl(private val api: GitHubAPI) : Repository {

    override suspend fun getAllUsers(): ApiResult<List<UsersListResponse>> {
        val result = getResult {
            api.getAllUsers()
        }
        return result
    }

    override suspend fun getSearchUser(userName: String): ApiResult<UserSearchListResponse> {
        val result = getResult {
            api.getSearchUser(userName)
        }
        return result
    }

    override suspend fun getUser(userName: String): ApiResult<UserResponse> {
        val result = getResult {
            api.getUser(userName)
        }
        return result
    }

    override suspend fun getUserRepositories(userName: String): ApiResult<List<UserRepositoriesResponse>> {
        val result = getResult {
            api.getUserRepositories(userName)
        }
        return result
    }
}