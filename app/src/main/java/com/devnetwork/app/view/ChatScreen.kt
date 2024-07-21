package com.devnetwork.app.view

import android.view.ViewTreeObserver
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import com.devnetwork.app.R
import com.devnetwork.app.model.User
import com.devnetwork.app.utils.Constants
import com.devnetwork.app.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavController, mainViewModel: MainViewModel, user: User) {
    val listState = rememberLazyListState()
    val messages by mainViewModel.messages.observeAsState(emptyList())
    val chatId = Constants.generateChatId(Constants.loggedUserId,user.id)
    mainViewModel.getChatHistory(chatId)
    LaunchedEffect(messages.size) {
        if(messages.isNotEmpty()){
            listState.scrollToItem(messages.size - 1)
        }
    }
    val view = LocalView.current
    val viewTreeObserver = view.viewTreeObserver
    DisposableEffect(viewTreeObserver) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val isKeyboardOpen = ViewCompat.getRootWindowInsets(view)
                ?.isVisible(WindowInsetsCompat.Type.ime()) ?: true

        }

        viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
    Scaffold(topBar = {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "back-arrow",
                        tint = colorResource(
                            id = R.color.white
                        )
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = colorResource(id = R.color.primary),
                titleContentColor = colorResource(id = R.color.white),
            ),
            title = {
                Text(
                    text = user.name, style = TextStyle(
                        color = colorResource(id = R.color.white),
                        fontSize = 24.sp
                    )
                )
            },
        )
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            // Chat messages
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                // Display chat messages
                LazyColumn(
                    state = listState,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    items(messages.size) { index ->
                        val message = messages[index]
                        val isCurrentUser = message.senderId == Constants.loggedUserId // Replace with your logic

                        if (!isCurrentUser) {
                            // Message sent by the current user, display on the right side
                            Column(modifier = Modifier.padding(top = 8.dp).wrapContentSize()) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 8.dp),
                                    horizontalArrangement = Arrangement.End,
                                ) {
                                    Text(
                                        text = message.message,
                                        style = TextStyle(
                                            color = colorResource(id = R.color.white)
                                        ),
                                        modifier = Modifier
                                            .background(color = colorResource(id = R.color.primary),
                                                shape = RoundedCornerShape(8.dp))
                                            .padding(8.dp)
                                    )
                                }
                                // Display timestamp below the message
                                Text(
                                    text = Constants.getTimeAgo(message.timestamp),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        textAlign = TextAlign.End
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 16.dp, end = 16.dp)
                                )
                            }
                        } else {
                            // Message sent by another user, display on the left side
                            Column(modifier = Modifier.padding(top = 8.dp)) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 8.dp),
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Text(
                                        text = message.message,
                                        style = TextStyle(
                                            color = colorResource(id = R.color.white)
                                        ),
                                        modifier = Modifier
                                            .background(color = colorResource(id = R.color.teal_700),
                                                shape = RoundedCornerShape(8.dp))
                                            .padding(8.dp)
                                    )

                                }
                                // Display timestamp below the message
                                Text(
                                    text = Constants.getTimeAgo(message.timestamp),
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 16.dp, end = 16.dp)
                                )
                            }
                        }
                    }
                }
            }

            InputBoxWrapper() {
                val timestamp = System.currentTimeMillis()
                val senderId = Constants.loggedUserId
                mainViewModel.saveChatMessage(chatId, senderId, it, timestamp)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputBoxWrapper(onClick: (message: String) -> Unit) {
    var text by remember { mutableStateOf("") }
    // Input area
    Column {
        Divider(color = Color.LightGray, thickness = 1.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            OutlinedTextField(

                placeholder = {
                    Text(text = "Write a message")
                },
                value = text,
                onValueChange = {
                    text = it
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                ),
                modifier = Modifier
                    .weight(1f)

            )

            TextButton(
                onClick = {
                    onClick(text)
                    text = ""
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(
                    text = "SEND", style = TextStyle(
                        color = colorResource(id = R.color.primary)
                    )
                )
            }
        }
    }
}