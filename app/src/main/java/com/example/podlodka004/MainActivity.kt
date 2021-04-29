package com.example.podlodka004

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.podlodka004.pages.details.DetailsScreen
import com.example.podlodka004.pages.home.MainScreen
import com.example.podlodka004.ui.theme.Podlodka004Theme

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Podlodka004Theme {
                Surface(color = MaterialTheme.colors.background) {
                    val viewModel: MainViewModel = viewModel()
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            MainScreen(viewModel = viewModel)
                        }
                        composable(route = "session-details/{sessionId}") {
                            val sessionId = requireNotNull(it.arguments?.getString("sessionId"))
                            DetailsScreen(
                                session = viewModel.getSessionById(sessionId),
                                onUpClick = viewModel::navigateUp,
                            )
                        }
                    }

                    viewModel.navEvents.observe(this) { event ->
                        when (event) {
                            is NavigationEvent.Home -> navController.navigate("home")
                            is NavigationEvent.Details -> navController.navigate("session-details/${event.sessionId}")
                            is NavigationEvent.Up -> navController.navigateUp()
                        }
                    }
                }
            }
        }
    }
}