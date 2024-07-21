package com.devnetwork.app.firebase

import com.devnetwork.app.model.ChatMessage
import com.devnetwork.app.model.User

interface FirebaseRepository {
    fun signUp(email: String, password: String,callback: (Boolean, String?) -> Unit)
    fun signIn(email: String, password: String,callback: (Boolean, String?) -> Unit)
    fun saveUserData(user: User)
    fun checkUserAuth():Boolean
    fun getLoggedUserId():String
    fun fetchUserDetails(id:String)
    fun logout()
    fun getAllUsers(loggedUserId:String, callback: (Boolean, List<User>) -> Unit)
    fun saveChatMessage(chatId:String,senderId:String,message:String,timeStamp:Long,callback: (Boolean) -> Unit)
    fun getChatHistory(chatId: String,callback: (Boolean, List<ChatMessage>) -> Unit)
}