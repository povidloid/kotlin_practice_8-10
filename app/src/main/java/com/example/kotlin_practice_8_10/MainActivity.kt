package com.example.kotlin_practice_8_10

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kotlin_practice_8_10.buttonNavigation.buttonNavigation
import com.example.kotlin_practice_8_10.database.MainDB
import com.example.kotlin_practice_8_10.screens.FirstScreen
import com.example.kotlin_practice_8_10.screens.SecondScreen
import com.example.kotlin_practice_8_10.screens.thirdScreen
import com.example.kotlin_practice_8_10.viewModel.MyViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val vModel = ViewModelProvider(this)[MyViewModel::class.java]
            val db = MainDB.getDb(this)
            val navController = rememberNavController()

            Scaffold(
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.largeTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = {
                            Text(
                                text = "Coolest app ever",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    )
                },
                bottomBar = { buttonNavigation(navController = navController) }
            ) { innerPadding ->
                Box(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "Screen1"
                    ) {
                        composable("Screen1") {
                            FirstScreen(vModel = vModel, db = db)
                        }
                        composable("Screen2") {
                            SecondScreen(db = db)
                        }
                        composable("Screen3") {
                            thirdScreen()
                        }
                    }

                }

            }
        }
    }
}