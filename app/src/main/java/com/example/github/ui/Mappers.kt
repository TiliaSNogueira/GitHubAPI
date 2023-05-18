package com.example.github.ui

import com.example.github.model.UserListModel
import com.example.github.model.UserRepositoriesModel
import com.example.github.remote.responses.Item
import com.example.github.remote.responses.UserRepositoriesResponse
import com.example.github.remote.responses.UsersListResponse

class UserListMapper : Mapper<List<UsersListResponse>, List<UserListModel>> {
    override fun mapFrom(from: List<UsersListResponse>): List<UserListModel> {
        return from.map {
            UserListModel(
                avatar_url = it.avatar_url,
                login = it.login,
                id = it.id,
            )
        }
    }
}

class UserSearchMapper : Mapper<List<Item>, List<UserListModel>> {
    override fun mapFrom(from: List<Item>): List<UserListModel> {
        return from.map {
            UserListModel(
                avatar_url = it.avatar_url,
                login = it.login,
                id = it.id,
            )
        }
    }
}

class UserRepositoryMapper : Mapper<List<UserRepositoriesResponse>, List<UserRepositoriesModel>> {
    override fun mapFrom(from: List<UserRepositoriesResponse>): List<UserRepositoriesModel> {
        return from.map {
            UserRepositoriesModel(
                id = it.id,
                repoFullName = it.full_name,
                private = it.private,
                ownerLogin = it.owner?.login,
                description = it.description,
                repoUrl = it.html_url
            )
        }
    }
}
