package com.devnetwork.app.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.devnetwork.app.R
import com.devnetwork.app.viewmodel.AuthViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.devnetwork.app.model.User
import com.devnetwork.app.utils.Constants


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SignUpScreen(navController: NavController, authViewModel: AuthViewModel = hiltViewModel()) {
    var name by remember {
        mutableStateOf("")
    }
    var username by remember {
        mutableStateOf("")
    }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val context = LocalContext.current


    Column(
        Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(
            text = stringResource(id = R.string.create_account_text),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.black)
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            stringResource(id = R.string.full_name_label),
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
            },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            placeholder = { Text(stringResource(id = R.string.full_name_label)) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.primary),
                unfocusedBorderColor = colorResource(id = R.color.primary),
                cursorColor = Color.Black,
            ),

            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            stringResource(id = R.string.user_name_label),
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
            },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            placeholder = { Text(stringResource(id = R.string.user_name_label)) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.primary),
                unfocusedBorderColor = colorResource(id = R.color.primary),
                cursorColor = Color.Black,
            ),

            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            stringResource(id = R.string.email_label),
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            placeholder = { Text(stringResource(id = R.string.email_label)) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.primary),
                unfocusedBorderColor = colorResource(id = R.color.primary),
                cursorColor = Color.Black,
            ),

            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            stringResource(id = R.string.password_label),
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it

            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.primary),
                unfocusedBorderColor = colorResource(id = R.color.primary),
                cursorColor = Color.Black
            ),
            placeholder = { Text(stringResource(id = R.string.password_label)) },
            maxLines = 1,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
//                    if (isLoginEnabled) {
//                        // Handle login button click here
//                        viewModel.performLogin(email, password, context)
//                    }
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),

            )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            stringResource(id = R.string.confirm_password_label),
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it

            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.primary),
                unfocusedBorderColor = colorResource(id = R.color.primary),
                cursorColor = Color.Black
            ),
            placeholder = { Text(stringResource(id = R.string.confirm_password_label)) },
            maxLines = 1,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
//                    if (isLoginEnabled) {
//                        // Handle login button click here
//                        viewModel.performLogin(email, password, context)
//                    }
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),

            )

        // Display error message if there is one
        if (authViewModel.errorMessage.isNotEmpty()) {
            Text(
                text = authViewModel.errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        if(authViewModel.loading){
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary
            )
        }
        else {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.primary) // Change colors accordingly
                ),
                shape = RoundedCornerShape(corner = CornerSize(10.dp)),
                onClick = {
                    authViewModel.validateAndSignUp(name,username,email, password,confirmPassword) { success, errorMessage ->

                        if (success) {

                            if (authViewModel.checkUserAuth()) {
                                Constants.loggedUserId = authViewModel.getLoggedUserId()
                                val user = User(
                                    authViewModel.getLoggedUserId(),
                                    name,
                                    username,
                                    email,
                                    System.currentTimeMillis(),
                                    System.currentTimeMillis()
                                )
                                Constants.loggedUser = user
                                authViewModel.saveUserData(user)
                                authViewModel.hideLoading()
                                navController.navigate("home"){
                                    popUpTo(navController.graph.id){
                                        inclusive = true
                                    }
                                }
                            }

                        } else {
                            authViewModel.hideLoading()
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.sign_up_text),
                    style = TextStyle(
                        fontSize = 22.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .align(Alignment.End)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.already_have_account_text),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.black)
                )
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                modifier = Modifier.clickable {
                    navController.navigate("login"){
                        popUpTo(navController.graph.id){
                            inclusive = true
                        }
                    }
                },
                text = stringResource(id = R.string.sign_in_button),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.primary)
                )
            )
        }
    }

}