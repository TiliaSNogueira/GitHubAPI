package com.example.github.remote.api

import com.example.github.remote.responses.UserRepositoriesResponse
import com.example.github.remote.responses.UserResponse
import com.example.github.remote.responses.UserSearchListResponse
import com.example.github.remote.responses.UsersListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubAPI {

    @GET("users")
    suspend fun getAllUsers(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int = 20
    ): Response<List<UsersListResponse>>

    @GET("search/users")
    suspend fun getSearchUser(
        @Query("q") userName: String
    ): Response<UserSearchListResponse>


    @GET("users/{userName}")
    suspend fun getUser(
        @Path("userName") userName: String
    ): Response<UserResponse>


    @GET("users/{userName}/repos")
    suspend fun getUserRepositories(
        @Path("userName") userName: String
    ): Response<List<UserRepositoriesResponse>>
}