package com.example.github.ui.userslist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github.model.UserListModel
import com.example.github.network.ApiResult
import com.example.github.network.ViewState
import com.example.github.remote.repository.Repository
import com.example.github.ui.UserListMapper
import com.example.github.ui.UserSearchMapper
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsersListViewModel(private val repository: Repository) : ViewModel() {

    var usersList = MutableLiveData<ViewState<List<UserListModel>>>()

    private val mapperUser = UserListMapper()
    private val mapperSearch = UserSearchMapper()


    fun getAllUsers() {
        viewModelScope.launch {
            usersList.value = ViewState.Loading
            try {
                when (val result = repository.getAllUsers()) {
                    is ApiResult.Success -> {
                        withContext(coroutineContext) {
                            val list = mapperUser.mapFrom(result.data)
                            usersList.value = ViewState.Success(list)
                        }
                    }
                    is ApiResult.Error -> {
                        withContext(coroutineContext) {
                            usersList.value = ViewState.Error(result.message)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("UsersListViewModel", "getAllUsers")
            }
        }
    }

    fun getSearchUser(userName: String) {
        viewModelScope.launch {
            usersList.value = ViewState.Loading
            try {
                when (val result = repository.getSearchUser(userName)) {
                    is ApiResult.Success -> {
                        withContext(coroutineContext) {
                            val list = mapperSearch.mapFrom(result.data.items)
                            usersList.value = ViewState.Success(list)
                        }
                    }
                    is ApiResult.Error -> {
                        withContext(coroutineContext) {
                            usersList.value = ViewState.Error(result.message)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("UsersListViewModel", "getSearchUser")
            }
        }
    }
}
