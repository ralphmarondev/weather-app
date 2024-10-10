package com.ralphmarondev.weatherapp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ralphmarondev.weatherapp.data.api.NetworkResponse
import com.ralphmarondev.weatherapp.data.api.WeatherModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    var city by remember { mutableStateOf("") }
    val weatherResult = viewModel.weatherResult.observeAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Weather App",
                        fontFamily = FontFamily.Monospace
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = city,
                        onValueChange = { city = it },
                        label = {
                            Text(
                                text = "Search for any location",
                                fontFamily = FontFamily.Monospace,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        textStyle = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W500
                        ),
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    viewModel.getData(city)
                                    keyboardController?.hide()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search Location"
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        maxLines = 1
                    )

                    when (val result = weatherResult.value) {
                        is NetworkResponse.Error -> {
                            Text(
                                text = result.message
                            )
                        }

                        NetworkResponse.Loading -> {
                            CircularProgressIndicator()
                        }

                        is NetworkResponse.Success -> {
                            WeatherDetails(data = result.data)
                        }

                        null -> {
                            Text(
                                text = "Hello there! Ralph is cute!",
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherDetails(data: WeatherModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location",
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Column {
                Text(text = data.location.name, fontSize = 30.sp, fontFamily = FontFamily.Monospace)
                Text(
                    text = data.location.country,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "${data.current.temp_c} °C",
            fontSize = 56.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        AsyncImage(
            modifier = Modifier.size(160.dp),
            model = "https:${data.current.condition.icon}".replace("64x65", "128x128"),
            contentDescription = "Condition"
        )
        Text(
            text = data.current.condition.text,
            fontSize = 20.sp,
            fontFamily = FontFamily.Monospace,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
    }
}