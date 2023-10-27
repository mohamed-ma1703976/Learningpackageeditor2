package com.example.learningpackageeditor.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.learningpackageeditor.Repository.UserRepository
import com.example.learningpackageeditor.model.User

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserRepository(application)

    // Add a MutableLiveData for the current user
    val user = MutableLiveData<User?>()

    init {
        repository.loadUsersFromJsonFile()
    }

    fun login(email: String, password: String): User? {
        val loggedInUser = repository.login(email, password)
        user.value = loggedInUser  // Update the current user
        return loggedInUser
    }

    fun signUp(user: User): Boolean {
        return repository.signUp(user)
    }
}
