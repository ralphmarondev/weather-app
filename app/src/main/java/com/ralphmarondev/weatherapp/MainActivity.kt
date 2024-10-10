package com.ralphmarondev.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.ralphmarondev.weatherapp.presentation.HomeScreen
import com.ralphmarondev.weatherapp.presentation.HomeViewModel
import com.ralphmarondev.weatherapp.ui.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        setContent {
            WeatherAppTheme {
                HomeScreen(viewModel)
            }
        }
    }
}