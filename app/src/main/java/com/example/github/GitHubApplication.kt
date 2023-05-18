package com.example.github

import android.app.Application
import com.example.github.di.dependencyProvider
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GitHubApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@GitHubApplication)
            modules(dependencyProvider)
        }
    }
}