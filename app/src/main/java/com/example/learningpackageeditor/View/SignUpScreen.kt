package com.example.learningpackageeditor.View
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.learningpackageeditor.ViewModel.UserViewModel
import com.example.learningpackageeditor.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(viewModel: UserViewModel, onSignedUp: (Boolean) -> Unit) {
    val firstNameState = remember { mutableStateOf("") }
    val lastNameState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val roleState = remember { mutableStateOf("Student") }  // Default role as "Student"

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = firstNameState.value,
            onValueChange = { firstNameState.value = it },
            label = { Text("First Name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = lastNameState.value,
            onValueChange = { lastNameState.value = it },
            label = { Text("Last Name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = emailState.value,
            onValueChange = { emailState.value = it },
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Here you can implement a dropdown or a RadioButtonGroup for Role selection.
        // I'm keeping it simple with a TextField for now.
        TextField(
            value = roleState.value,
            onValueChange = { roleState.value = it },
            label = { Text("Role (Teacher/Student)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val newUser = User(
                firstName = firstNameState.value,
                lastName = lastNameState.value,
                email = emailState.value,
                password = passwordState.value,
                photoUri = "",  // You can add functionality to upload and fetch photo URI.
                role = roleState.value
            )
            val success = viewModel.signUp(newUser)
            onSignedUp(success)
        }) {
            Text("Sign Up")
        }
    }
}
