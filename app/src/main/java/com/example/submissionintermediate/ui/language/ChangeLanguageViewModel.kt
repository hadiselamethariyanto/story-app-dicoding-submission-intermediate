package com.example.submissionintermediate.ui.language

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissionintermediate.data.preferences.UserPreference
import kotlinx.coroutines.launch

class ChangeLanguageViewModel(private val userPreference: UserPreference) : ViewModel() {

    fun changeLanguage(code: String) = viewModelScope.launch { userPreference.saveLanguage(code) }

}