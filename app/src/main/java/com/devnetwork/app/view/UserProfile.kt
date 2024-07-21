package com.devnetwork.app.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.devnetwork.app.R
import com.devnetwork.app.model.User
import com.devnetwork.app.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfile(navController: NavController){

Scaffold(topBar = {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = {
              navController.popBackStack()
            }){
                Icon(Icons.Default.ArrowBack, contentDescription = "back-arrow", tint = colorResource(
                    id = R.color.white
                ))
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.primary),
            titleContentColor = colorResource(id = R.color.white),
        ),
        title = {
            Text(text = stringResource(id = R.string.personal_info), style = TextStyle(
                color = colorResource(id = R.color.white),
                fontSize = 24.sp
            )
            )
        },
        )
}) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(it),
        horizontalAlignment = Alignment.Start
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(14.dp)
        ) {
           Text(
               modifier = Modifier.weight(1f),
               text = "Name", style = TextStyle(
               color = colorResource(id = R.color.black),
               fontSize = 16.sp,
               fontWeight = FontWeight.Bold
           ))
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = Constants.loggedUser!!.name, style = TextStyle(
                color = colorResource(id = R.color.primary),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ))
        }


        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(14.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Username", style = TextStyle(
                color = colorResource(id = R.color.black),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ))
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = Constants.loggedUser!!.username, style = TextStyle(
                color = colorResource(id = R.color.primary),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ))
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(14.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Email", style = TextStyle(
                    color = colorResource(id = R.color.black),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ))
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = Constants.loggedUser!!.email, style = TextStyle(
                color = colorResource(id = R.color.primary),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ))
        }
    }
}

}