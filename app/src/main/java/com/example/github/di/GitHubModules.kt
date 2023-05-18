package com.example.github.di

import com.example.github.remote.repository.Repository
import com.example.github.remote.repository.RepositoryImpl
import com.example.github.ui.user.UserViewModel
import com.example.github.ui.userslist.UsersListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dependencyProvider = module {

    single {
        RetrofitProvider(androidContext()).api
    }

    single<Repository> {
        RepositoryImpl(api = get())
    }

    viewModel {
        UsersListViewModel(
            repository = get()
        )
    }

    viewModel {
        UserViewModel(
            repository = get()
        )
    }

}