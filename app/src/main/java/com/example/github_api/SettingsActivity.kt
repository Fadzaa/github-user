package com.example.github_api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable.Factory
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.example.github_api.databinding.ActivitySettingsBinding
import com.example.github_api.viewmodel.MainViewModel
import com.example.github_api.viewmodel.MainViewModelFactory

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    private lateinit var pref: SettingPreferences
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(pref)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = SettingPreferences.getInstance(application.dataStore)

        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchButton.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchButton.isChecked = false
            }
        }

        binding.switchButton.setOnCheckedChangeListener { _, isChecked ->
           mainViewModel.saveThemeSetting(isChecked )
        }
    }
}