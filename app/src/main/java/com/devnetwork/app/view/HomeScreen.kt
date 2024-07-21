package com.devnetwork.app.view

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devnetwork.app.R
import com.devnetwork.app.model.User
import com.devnetwork.app.utils.Constants
import com.devnetwork.app.viewmodel.MainViewModel
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeUserList(navController: NavController, mainViewModel: MainViewModel = hiltViewModel()) {
    mainViewModel.fetchUserDetails(Constants.loggedUserId)
    val users by mainViewModel.users.observeAsState(emptyList())
    val isLoading by mainViewModel.loading.collectAsState()
    val context = LocalContext.current
    var showLogoutDialog by remember { mutableStateOf(false) }

    LaunchedEffect(true){
        mainViewModel.getAllUsers(Constants.loggedUserId)
    }

    Scaffold(topBar = {
        TopAppBar(
            colors = topAppBarColors(
                containerColor = colorResource(id = R.color.primary),
                titleContentColor = colorResource(id = R.color.white),
            ),
            title = {
                Text(text = stringResource(id = R.string.app_name), style = TextStyle(
                    color = colorResource(id = R.color.white),
                    fontSize = 24.sp
                ))
            },
            actions = {
                Row {
                    IconButton(onClick = {
                        showLogoutDialog = true
                    }){
                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout", tint = colorResource(
                            id = R.color.white
                        ))
                    }
                    IconButton(onClick = {
                        navController.navigate("profile")
                    }){
                        Icon(Icons.Outlined.AccountCircle, contentDescription = "account", tint = colorResource(
                            id = R.color.white
                        ))
                    }
                }
            },

            )
    }) {

        // Logout confirmation dialog
        if (showLogoutDialog) {
            LogoutConfirmationDialog(
                onConfirm = {
                    mainViewModel.logout()
                    Constants.loggedUserId = ""
                    Constants.loggedUser = null
                    showLogoutDialog = false
                    navController.navigate("login"){
                        popUpTo(navController.graph.id){
                            inclusive = true
                        }
                    }
                },
                onDismiss = { showLogoutDialog = false }
            )
        }

            if(isLoading){
                Loading()
            }
                else {
                if (users.isEmpty()) {
                    EmptyListMessage()
                } else {
                    Box(
                        Modifier
                            .background(Color.White)
                            .padding(it)
                            .fillMaxSize()
                    ) {
                        LazyColumn {
                            items(users.size) { index ->
                                val user = users[index]
                                UserListItem(user = user) {
                                    val json = Uri.encode(Gson().toJson(user))
                                    navController.navigate("chat/$json")
                                }
                                // Add a divider between items, except for the last item
                                if (index < users.size - 1) {
                                    Divider(color = Color.LightGray, thickness = 1.dp)
                                }
                            }
                        }
                    }
                }
            }
    }
}

@Composable
fun UserListItem(user: User,onClick:(user: User)->Unit){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                onClick(user)
            }
            .padding(16.dp)
    ) {

        Text(text = user.name, style = TextStyle(
            fontSize = 22.sp,
            color = colorResource(id = R.color.black),
            fontWeight = FontWeight.Bold
        )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = user.email, style = TextStyle(
            fontSize = 18.sp,
        )
        )
    }
}

@Composable
fun LogoutConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = "Logout",
                style = MaterialTheme.typography.headlineLarge
            )
        },
        text = {
            Text("Are you sure you want to logout?")
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text("Logout")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun EmptyListMessage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(imageVector = Icons.Outlined.Warning, contentDescription = null, modifier = Modifier.size(48.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text("No users found.", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun Loading() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}