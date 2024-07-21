package com.devnetwork.app.types

import android.os.Bundle
import androidx.navigation.NavType
import com.devnetwork.app.model.User
import com.google.gson.Gson

class UserType : NavType<User>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): User? {
        return bundle.getSerializable(key) as User?
    }
    override fun parseValue(value: String): User {
        return Gson().fromJson(value, User::class.java)
    }
    override fun put(bundle: Bundle, key: String, value: User) {
        bundle.putSerializable(key, value)
    }
}