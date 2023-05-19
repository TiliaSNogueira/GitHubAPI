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
    var usersListSearch = MutableLiveData<ViewState<List<UserListModel>>>()
    private val mapperUser = UserListMapper()
    private val mapperSearch = UserSearchMapper()

    private var since: Int = 0


    fun getAllUsers() {
        viewModelScope.launch {
            usersList.value = ViewState.Loading
            try {
                when (val result = repository.getAllUsers(since)) {
                    is ApiResult.Success -> {
                        withContext(coroutineContext) {
                            val list = mapperUser.mapFrom(result.data)
                            usersList.value = ViewState.Success(list)
                        }
                        since = +20
                    }
                    is ApiResult.Error -> {
                        withContext(coroutineContext) {
                            val message = result.message.substringAfter(":").substringBefore(",")
                            usersList.value = ViewState.Error(message)
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
            usersListSearch.value = ViewState.Loading
            try {
                when (val result = repository.getSearchUser(userName)) {
                    is ApiResult.Success -> {
                        withContext(coroutineContext) {
                            val list = mapperSearch.mapFrom(result.data.items)
                            usersListSearch.value = ViewState.Success(list)
                        }
                    }
                    is ApiResult.Error -> {
                        withContext(coroutineContext) {
                            val message = result.message.substringAfter(":").substringBefore(",")
                            usersListSearch.value = ViewState.Error(message)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("UsersListViewModel", "getSearchUser")
            }
        }
    }


}
