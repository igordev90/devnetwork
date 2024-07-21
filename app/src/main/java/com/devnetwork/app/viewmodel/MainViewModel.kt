package com.devnetwork.app.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devnetwork.app.firebase.FirebaseRepository
import com.devnetwork.app.model.ChatMessage
import com.devnetwork.app.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _isLoading

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    private val _messages = MutableLiveData<List<ChatMessage>>()
    val messages: LiveData<List<ChatMessage>> get() = _messages

    fun logout() {
        firebaseRepository.logout()
    }

    fun getAllUsers(loggedUserId:String) {
        _isLoading.value = true
        firebaseRepository.getAllUsers(loggedUserId){success, list->
            Log.d("TEST1000","$success:$list")
            _users.postValue(list)
            _isLoading.value = false
        }
    }

    fun saveChatMessage(chatId:String,senderId:String,message:String,timeStamp:Long){
        firebaseRepository.saveChatMessage(chatId,senderId,message,timeStamp){
            getChatHistory(chatId)
        }
    }

    fun getChatHistory(chatId: String){
        firebaseRepository.getChatHistory(chatId){success, list->
            _messages.postValue(list)
        }
    }

    fun fetchUserDetails(id:String){
        firebaseRepository.fetchUserDetails(id)
    }

}