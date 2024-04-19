package com.example.github_api.viewmodel_injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.github_api.model.preferences.SettingPreferences
import com.example.github_api.viewmodel.ThemeViewModel

class ThemeViewModelFactory (private val pref: SettingPreferences) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ThemeViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
