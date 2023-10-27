package com.example.learningpackageeditor.Repository

import android.content.Context
import com.example.learningpackageeditor.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader

class UserRepository(private val context: Context) {

    private val users = mutableListOf<User>()
    private val gson = Gson()

    fun login(email: String, password: String): User? {
        return users.find { it.email == email && it.password == password }
    }

    fun signUp(user: User): Boolean {
        if (users.any { it.email == user.email }) {
            return false  // Email already exists
        }
        users.add(user)
        return true
    }

    fun loadUsersFromJsonFile() {
        val jsonString = readJsonFile("users.json")
        val userListType = object : TypeToken<List<User>>() {}.type
        users.addAll(gson.fromJson(jsonString, userListType))
    }

    private fun readJsonFile(filename: String): String {
        val inputStream = context.assets.open(filename)
        val bufferedReader = BufferedReader(inputStream.reader())
        return bufferedReader.use { it.readText() }
    }
}
