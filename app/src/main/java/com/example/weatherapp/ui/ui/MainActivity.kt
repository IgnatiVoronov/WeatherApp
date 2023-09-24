package com.example.weatherapp.ui.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherapp.ui.HomeScreenViewModel
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onStart() {
        super.onStart()
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ){ permissions ->
            when{
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION,false)->{
                    Log.d("TAG", "FINE LOCATION PERMISSION")
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION,false)->{
                    Log.d("TAG", "COARSE LOCATION PERMISSION")
                }
                else->{
                    Log.d("TAG", "NO LOCATION PERMISSION")
                }
            }
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
            return
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            Log.d("TAG", "${location.latitude}, ${location.longitude}")
            val viewModel: HomeScreenViewModel by viewModels()
            viewModel.setLocation(location)
        }

    }

    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val viewModel = hiltViewModel<HomeScreenViewModel>()
                    val data by viewModel.weatherFlow.collectAsStateWithLifecycle()



                    LaunchedEffect(key1 = Unit) {
                        viewModel.launchLongPolling()
                    }

                    MainScreen(
                        temperature = "temperature: "+data?.temperature.toString(),
                        humidity = "humidity: "+data?.humidity.toString(),
                        city = "Undefined",
                        location = "location: ${viewModel.locationLatitude.value} ${viewModel.locationLongitude.value}"
                    )
                }
            }
        }

    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    temperature: String,
    humidity: String,
    city: String,
    location: String
) {
    Column(modifier = modifier) {
        Text(
            text = temperature,
            fontSize = 16.sp,
            color = Color.Gray
        )
        Text(
            text = humidity,
            fontSize = 16.sp,
            color = Color.Gray
        )
        Text(
            text = city,
            fontSize = 32.sp,
            color = Color.Black
        )
        Text(
            text = location,
            fontSize = 16.sp,
            color = Color.Gray
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xfff)
@Composable
fun MainScreenPreview() {
    WeatherAppTheme {
        MainScreen(
            temperature = "16 C",
            humidity = "76 %",
            city = "Barcelona",
            location = "Location"
        )

    }
}