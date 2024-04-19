package com.example.github_api.di

import com.example.github_api.model.preferences.SettingPreferences
import com.example.github_api.model.preferences.dataStore
import com.example.github_api.model.remote.ApiConfig
import com.example.github_api.model.remote.ApiService
import com.example.github_api.viewmodel.DetailViewModel
import com.example.github_api.viewmodel.FavouriteViewModel
import com.example.github_api.viewmodel.MainViewModel
import com.example.github_api.viewmodel.RepositoryViewModel
import com.example.github_api.viewmodel.SearchViewModel
import com.example.github_api.viewmodel.ThemeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<ApiService> {
        ApiConfig.getApiService()
    }

    viewModel { MainViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { ThemeViewModel(SettingPreferences.getInstance(androidApplication().dataStore)) }
    factory { (username: String) -> RepositoryViewModel(username, get()) }
    factory { (username: String) -> DetailViewModel(username, get()) }
    factory { FavouriteViewModel(androidApplication()) }

}
