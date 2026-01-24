package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.ui.theme.WeatherAppTheme
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException
import java.io.IOException
import android.widget.Button
import androidx.compose.ui.semantics.Role.Companion.Button
import com.example.weatherapp.ui.theme.RetrofitInstance
import androidx.compose.material3.Button
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

const val API_KEY = "7b8fe256e0252645ed167848cb976316"

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
fun Greeting(modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()

    var cityName by remember { mutableStateOf("") }
    var temperature by remember { mutableStateOf("Unknown") }

    Column(
        modifier = modifier.fillMaxSize(), // tells Column to take up the entire available space
        horizontalAlignment = Alignment.CenterHorizontally, // centers the content of Column horizontally
        verticalArrangement = Arrangement.Center // centers the content of Column vertically
    ) {
        TextField(
            value = cityName,
            onValueChange = { cityName = it },
            label = { Text("Enter city name") }
        )

        Button(onClick = {
            // 2. Launch a coroutine when clicked
            coroutineScope.launch {
                // ... inside coroutineScope.launch
                try {
                    val response = RetrofitInstance.api.getWeather(cityName, API_KEY)
                    temperature = "${response.main.temperature}°F"
            }   catch (e: retrofit2.HttpException) {
                    // The server responded, but with an error code
                    if (e.code() == 404) {
                        temperature = "City not found!"
                    } else if (e.code() == 401) {
                        temperature = "Invalid API key"
                    } else {
                        temperature = "Server Error: ${e.code()}"
                    }
            }   catch (e: java.io.IOException) {
                        // The internet connection failed (no WiFi, airplane mode, etc.)
                        temperature = "Check your internet connection"
            }   catch (e: Exception) {
                    temperature = "Unknown Error: ${e.message}"
            }

         }
    })  {
            Text("Get Weather")
        }

        Text(text = "Temperature: $temperature")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherAppTheme {
        Greeting()
    }
}