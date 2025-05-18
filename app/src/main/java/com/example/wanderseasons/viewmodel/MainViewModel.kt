package com.example.wanderseasons.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wanderseasons.model.LoginRequest
import com.example.wanderseasons.model.LoginResponse
import com.example.wanderseasons.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _loginResult = MutableLiveData<LoginResponse>()
    val loginResult: LiveData<LoginResponse> = _loginResult

    private val _dashboardData = MutableLiveData<List<Map<String, Any>>>()
    val dashboardData: LiveData<List<Map<String, Any>>> = _dashboardData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun login(campus: String, username: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.login(campus, LoginRequest(username, password))
                if (response.isSuccessful) {
                    _loginResult.postValue(response.body())
                } else {
                    _error.postValue("Login failed: ${response.code()}")
                }
            } catch (e: Exception) {
                _error.postValue("Login error: ${e.localizedMessage}")
            }
        }
    }

    fun fetchDashboard(keypass: String) {
        viewModelScope.launch {
            try {
                val response = repository.getDashboard(keypass)
                if (response.isSuccessful) {
                    val body = response.body()
                    _dashboardData.postValue(body?.entities)
                } else {
                    _error.postValue("Failed to load dashboard: ${response.code()}")
                }
            } catch (e: Exception) {
                _error.postValue("Dashboard error: ${e.localizedMessage}")
            }
        }
    }

}
