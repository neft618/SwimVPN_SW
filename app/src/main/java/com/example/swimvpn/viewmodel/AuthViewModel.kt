package com.example.swimvpn.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.swimvpn.data.database.UserDatabaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val userDatabaseHelper = UserDatabaseHelper(application)
    private val sharedPreferences = application.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    init {
        // Check if the user is already authenticated
        val isAuthenticated = sharedPreferences.getBoolean("isAuthenticated", false)
        _isAuthenticated.value = isAuthenticated
        if (isAuthenticated) {
            _username.value = sharedPreferences.getString("username", "") ?: ""
        }
    }

    fun authenticate(username: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val isAuthenticated = withContext(Dispatchers.IO) {
                val db = userDatabaseHelper.readableDatabase
                val cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", arrayOf(username, password))
                val isAuthenticated = cursor.count > 0
                cursor.close()
                isAuthenticated
            }
            _isAuthenticated.value = isAuthenticated
            if (isAuthenticated) {
                _username.value = username
                sharedPreferences.edit().putBoolean("isAuthenticated", true).putString("username", username).apply()
            }
            onResult(isAuthenticated)
        }
    }

    fun logout() {
        _isAuthenticated.value = false
        _username.value = ""
        sharedPreferences.edit().putBoolean("isAuthenticated", false).remove("username").apply()
    }
}