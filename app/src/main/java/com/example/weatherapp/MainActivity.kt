package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.ui.theme.WeatherAppTheme
import data.local.WeatherReport
import com.example.weatherapp.ui.WeatherViewModel

// MainActivity is responsible for setting up the UI and handling user interactions
class MainActivity : ComponentActivity() {
    // onCreate is called when the activity is first created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // acts as a bridge between the UI and the app's logic
        enableEdgeToEdge()
        // responsible for setting up the UI
        setContent {
            // sets the theme for the app
            WeatherAppTheme {
                // sets up the main screen of the app
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // sets up the content of the main screen
                    WeatherScreen(modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

// WeatherScreen is the main screen of the app
@Composable
fun WeatherScreen(modifier: Modifier = Modifier, viewModel: WeatherViewModel = viewModel(factory = WeatherViewModel.Factory)) {
    // collects the state of the weather list from the view model
    val uiWeatherList by viewModel.weatherList.collectAsState(initial = emptyList())

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

        // Button that calls the fetchWeather function when clicked
        Button(onClick = {
            viewModel.fetchWeather(BuildConfig.API_KEY)
        }) {
            Text("Get Weather")
        }

        // displays the weather list if it's not loading, otherwise displays a loading message
        if (viewModel.isLoading) {
            Text("Loading...")
        } else {
            LazyColumn (
                modifier = Modifier.fillMaxWidth()
            ) {
                items(uiWeatherList) { report ->
                    WeatherRow(report = report)
                    HorizontalDivider()
                }
            }
        }
    }
}

// WeatherRow displays a single weather report
@Composable
fun WeatherRow(report: WeatherReport) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = report.cityName, style = MaterialTheme.typography.headlineMedium)
            Text(text = report.condition, style = MaterialTheme.typography.bodyMedium)
        }
        val temperatureText = if (report.temperature.isNaN()) "N/A" else "${report.temperature}°F"
        Text(text = temperatureText, style = MaterialTheme.typography.titleLarge)
    }
}

// GreetingPreview is used to preview the WeatherScreen in Android Studio
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherAppTheme {
        WeatherScreen()
    }
}
