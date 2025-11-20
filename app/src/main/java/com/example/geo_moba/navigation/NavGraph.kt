package com.example.geo_moba.navigation // Capa de navegación.

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

// ✅ IMPORTS CORRECTOS — uno por cada pantalla
import com.example.geo_moba.ui.screen.splash.SplashScreen
import com.example.geo_moba.ui.screen.login.LoginScreen
import com.example.geo_moba.ui.screen.register.RegisterScreen
import com.example.geo_moba.ui.screen.home.HomeScreen
import com.example.geo_moba.ui.screen.map.MapScreen

@Composable
fun GeoMobaNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.Splash
    ) {
        composable(Routes.Splash) { SplashScreen(navController) }
        composable(Routes.Login) { LoginScreen(navController) }
        composable(Routes.Register) { RegisterScreen(navController) }
        composable(Routes.Home) { HomeScreen(navController) }
        composable(Routes.Map) { MapScreen(navController) }
    }
}
