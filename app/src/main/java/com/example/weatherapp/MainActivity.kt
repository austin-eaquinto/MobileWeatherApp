package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.ui.theme.WeatherViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier, viewModel: WeatherViewModel = viewModel()) {

    Column(
        modifier = modifier.fillMaxSize(), // tells Column to take up the entire available space
        horizontalAlignment = Alignment.CenterHorizontally, // centers the content of Column horizontally
        verticalArrangement = Arrangement.Center // centers the content of Column vertically
    ) {
        TextField(
            value = viewModel.cityName,
            onValueChange = { newText -> viewModel.cityName = newText },
            label = { Text("Enter city name") }
        )

        Button(onClick = {
            viewModel.fetchWeather(BuildConfig.API_KEY)
        }) {
            Text("Get Weather")
        }

        if (viewModel.isLoading) {
            Text("Loading...")
        } else {
            Text("Temperature: ${viewModel.temperature}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherAppTheme {
        Greeting()
    }
}
