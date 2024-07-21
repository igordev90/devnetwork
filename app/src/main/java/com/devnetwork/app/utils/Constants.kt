package com.devnetwork.app.utils

import com.devnetwork.app.model.User
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Constants {
    companion object{
        var loggedUserId:String = ""
        var loggedUser: User? = null

        fun generateChatId(user1Id: String, user2Id: String): String {
            val participants = listOf(user1Id, user2Id).sorted()
            return participants.joinToString("_")
        }

        fun getTimeAgo(timestamp: Long): String {
            // Implement your logic to convert timestamp to a human-readable format (e.g., "2 hours ago")
            return SimpleDateFormat("hh:mm a", Locale.getDefault())
                .format(Date(timestamp))
        }
    }
}