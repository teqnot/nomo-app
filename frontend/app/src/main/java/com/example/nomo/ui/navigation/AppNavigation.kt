//package com.example.nomo.ui.navigation
//
//import androidx.compose.runtime.Composable
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import com.example.nomo.ui.addticket.AddTicketScreen
//import com.example.nomo.ui.auth.FirstLogin
//import com.example.nomo.ui.auth.RegistrationScreen
//import com.example.nomo.ui.auth.LoginScreen
//import com.example.nomo.ui.mainpage.MainScreen
//
//@Composable
//fun AppNavigation(
//    navController: NavHostController = rememberNavController(),
//    startDestination: String = "add_entry" //Правильный - "firstlogin", тестовый - "add_ticket" 11/05/2025
//) {
//    NavHost(
//        navController = navController,
//        startDestination = startDestination
//    ) {
//        composable("firstlogin") {
//            FirstLogin(
//                onNavigateToRegistration = {
//                    navController.navigate("registration")
//                },
//                onNavigateToLogin = {
//                    navController.navigate("login")
//                }
//            )
//        }
//
//        composable("registration") {
//            RegistrationScreen(
//                onBack = { navController.popBackStack() },
//                onRegisterSuccess = {
//                    navController.navigate("main") {
//                        popUpTo("firstlogin") { inclusive = true }
//                    }
//                },
//                viewModel = hiltViewModel() ,
//                onLoginPressed = {
//                    navController.navigate("login") {
//                        popUpTo("registration") { inclusive = true }
//                    }
//                }
//            )
//        }
//
//        composable("login") {
//            LoginScreen(
//                onBack = { navController.popBackStack() },
//                onLoginSuccess = {
//                    navController.navigate("main") {
//                        popUpTo("firstlogin") { inclusive = true }
//                    }
//                },
//                viewModel = hiltViewModel() ,
//                onRegisterPressed = {
//                    navController.navigate("registration") {
//                        popUpTo("login") { inclusive = true }
//                    }
//                }
//            )
//        }
//
//        composable("main") {
//            MainScreen()
//        }
//
//        composable("add_entry") {
//            AddTicketScreen()
//        }
//    }
//}