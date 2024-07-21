package com.devnetwork.app.firebase

import android.util.Log
import com.devnetwork.app.model.ChatMessage
import com.devnetwork.app.model.User
import com.devnetwork.app.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject


class FirebaseRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase
) : FirebaseRepository {
    private val usersRef: DatabaseReference = database.reference.child("Users")
    private val chatsRef: DatabaseReference = database.reference.child("Chats")

    override fun signUp(email: String, password: String,callback: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // User signed up successfully
                    callback(true, null)
                } else {
                    // Sign up failed
                    callback(false, task.exception?.message)
                }
            }
    }

    override fun signIn(email: String, password: String,callback: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // User signed in successfully
                    callback(true, null)
                } else {
                    // Sign in failed
                    callback(false, task.exception?.message)
                }
            }
    }

    override fun saveUserData(user: User) {
        val userId = auth.currentUser?.uid
        userId?.let {
            usersRef.child(it).setValue(user)
        }
    }

    override fun checkUserAuth(): Boolean {
        if(auth.currentUser != null){
            Constants.loggedUserId = auth.currentUser!!.uid
        }
        return auth.currentUser != null
    }

    override fun getLoggedUserId(): String {
        return auth.currentUser!!.uid
    }

    override fun fetchUserDetails(id: String) {
        usersRef.child(id).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // User data found
                    val fetchUser = dataSnapshot.getValue(User::class.java)
                    Constants.loggedUser = fetchUser
                } else {
                    // User not found
                    Log.d("Firebase", "User not found")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Firebase", "Error fetching user data", databaseError.toException())
            }
        })
    }

    override fun logout() {
        auth.signOut()
    }

    override fun getAllUsers(loggedUserId:String,callback: (Boolean, List<User>) -> Unit) {
        val list = mutableListOf<User>()
        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (userSnapshot in dataSnapshot.children) {
                        val user = userSnapshot.getValue(User::class.java)
                        if (user != null && user.id != loggedUserId) {
                            list.add(user)
                        }
                    }
                   callback(true,list)
                } else {
                    // Handle the case when no users are found
                    callback(true,list)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("TEST1000",databaseError.message)
                // Handle errors
                callback(false,list)
            }
        })


    }

    override fun saveChatMessage(chatId: String, senderId: String, message: String,timeStamp:Long,callback: (Boolean) -> Unit) {
        val messageId = chatsRef.child(chatId).child("messages").push().key

        val messageData = mapOf(
            "senderId" to senderId,
            "message" to message,
            "timestamp" to timeStamp
        )

        if (messageId != null) {
            chatsRef.child(chatId).child("messages").child(messageId).setValue(messageData).addOnCompleteListener {
                callback(true)
            }
        }
    }

    override fun getChatHistory(chatId: String, callback: (Boolean, List<ChatMessage>) -> Unit) {
        val messages: MutableList<ChatMessage> = mutableListOf()
        chatsRef.child(chatId).child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Retrieve and process messages from dataSnapshot
                    if(dataSnapshot.exists()){
                        for (messageSnapshot in dataSnapshot.children) {
                            val message = messageSnapshot.getValue(ChatMessage::class.java)
                            if (message != null) {
                                messages.add(message)
                            }
                        }
                        callback(true,messages)
                    }
                    else{
                        callback(true,messages)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors
                    callback(false,messages)
                }
            })
    }
}