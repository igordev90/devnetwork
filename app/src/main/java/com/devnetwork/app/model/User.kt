package com.devnetwork.app.model

import java.io.Serializable


data class User(
    val id: String = "",
    val name: String = "",
    val username: String = "",
    val email: String = "",
    val createdAt: Long = 0,
    val updatedAt: Long = 0
) : Serializable{
    constructor():this("","","","",0,0)
}