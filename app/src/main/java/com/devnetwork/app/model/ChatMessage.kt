package com.devnetwork.app.model

import java.io.Serializable

data class ChatMessage(
    val senderId: String,
    val message: String,
    val timestamp: Long
):Serializable{
    constructor():this("","",0)
}