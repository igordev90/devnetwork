package com.devnetwork.app.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.devnetwork.app.model.User
import com.devnetwork.app.types.UserType
import com.devnetwork.app.ui.theme.DevNetworkTheme
import com.devnetwork.app.utils.Constants
import com.devnetwork.app.viewmodel.AuthViewModel
import com.devnetwork.app.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //Test version
    private val authViewModel: AuthViewModel by viewModels()
    private val mainViewModel:MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DevNetworkTheme {
                App()
            }
        }
    }

    @Composable
    private fun App() {
        val navController = rememberNavController()
        val startDestination = if(authViewModel.checkUserAuth()){
            "home"
        }
        else{
            "login"
        }
        NavHost(navController = navController, startDestination = startDestination){
            composable(route = "login",
                enterTransition = {
                    fadeIn(animationSpec = tween(500))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(500))
                }
                ){
                LoginScreen(navController,authViewModel)
            }
            composable(route = "signup",
                enterTransition = {
                    fadeIn(animationSpec = tween(500))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(500))
                }
                ){
                SignUpScreen(navController,authViewModel)
            }

            composable(route = "home",
                enterTransition = {
                    fadeIn(animationSpec = tween(500))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(500))
                }
                ){
                HomeUserList(navController, mainViewModel)
            }

            composable(route = "profile",
                enterTransition = {
                    fadeIn(animationSpec = tween(500))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(500))
                }
            ){
                UserProfile(navController)
            }

            composable(route = "chat/{user}",
                enterTransition = {
                    fadeIn(animationSpec = tween(500))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(500))
                },
                arguments = listOf(
                    navArgument("user"){
                        type = UserType()
                    }
                )
            ){
                val user = it.arguments?.getSerializable("user") as User
                ChatScreen(navController,mainViewModel,user)
            }
        }
    }
}