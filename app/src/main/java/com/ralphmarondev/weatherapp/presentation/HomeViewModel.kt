package com.ralphmarondev.weatherapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.weatherapp.data.api.NetworkResponse
import com.ralphmarondev.weatherapp.data.api.RetrofitInstance
import com.ralphmarondev.weatherapp.data.api.WeatherModel
import com.ralphmarondev.weatherapp.data.util.Constant
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val weatherApi = RetrofitInstance.weatherApi
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult: LiveData<NetworkResponse<WeatherModel>> = _weatherResult

    fun getData(city: String) {
        _weatherResult.value = NetworkResponse.Loading

        viewModelScope.launch {
            try {
                val response = weatherApi.getWeather(Constant.API_KEY, city)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _weatherResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _weatherResult.value = NetworkResponse.Error("Failed to load data")
                }
            } catch (e: Exception) {
                _weatherResult.value = NetworkResponse.Error("Failed to load data")
            }
        }
    }
}