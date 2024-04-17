package com.example.github_api.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.github_api.SettingPreferences

class MainViewModelFactory (private val pref: SettingPreferences) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
