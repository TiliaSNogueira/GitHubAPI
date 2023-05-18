package com.example.github.ui.user

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github.model.UserModel
import com.example.github.model.UserRepositoriesModel
import com.example.github.network.ApiResult
import com.example.github.network.ViewState
import com.example.github.remote.repository.Repository
import com.example.github.ui.UserRepositoryMapper
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(private val repository: Repository) : ViewModel() {

    var user = MutableLiveData<ViewState<UserModel>>()

    var repositories = MutableLiveData<ViewState<List<UserRepositoriesModel>>>()

    private val mapperRepository = UserRepositoryMapper()

    fun getUser(userName: String) {
        viewModelScope.launch {
            user.value = ViewState.Loading
            try {
                when (val result = repository.getUser(userName)) {
                    is ApiResult.Success -> {
                        withContext(coroutineContext) {
                            val userFromApi = UserModel(
                                avatar_url = result.data.avatar_url,
                                login = result.data.login,
                                userName = result.data.name,
                                userUrl = result.data.html_url,
                                company = result.data.company,
                                location = result.data.location
                            )
                            user.value = ViewState.Success(userFromApi)
                        }
                    }
                    is ApiResult.Error -> {
                        withContext(coroutineContext) {
                            val message = result.message.substringAfter(":").substringBefore(",")
                            user.value = ViewState.Error(message)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("UserViewModel", "getUsers")
            }
        }
    }

    fun getUserRepositories(userName: String) {
        viewModelScope.launch {
            repositories.value = ViewState.Loading
            try {
                when (val result = repository.getUserRepositories(userName)) {
                    is ApiResult.Success -> {
                        withContext(coroutineContext) {
                            val list = mapperRepository.mapFrom(result.data)
                            repositories.value = ViewState.Success(list)
                        }
                    }
                    is ApiResult.Error -> {
                        withContext(coroutineContext) {
                            val message = result.message.substringAfter(":").substringBefore(",")
                            repositories.value = ViewState.Error(message)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("UserRepositoriesViewModel", "getUserRepositories")
            }
        }
    }
}