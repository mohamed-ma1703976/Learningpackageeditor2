package com.example.learningpackageeditor.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.learningpackageeditor.R
import com.example.learningpackageeditor.model.User
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    login: (String, String) -> User?,
    onLoggedIn: (User) -> Unit
) {
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val showSnackBar = remember { mutableStateOf(false) }
    val updatedOnLoggedIn = rememberUpdatedState(onLoggedIn)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center)
        ) {
            // Add your image here
            Image(
                painter = painterResource(id = R.drawable.edu),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = emailState.value,
                onValueChange = { emailState.value = it },
                label = { Text("Email") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            TextField(
                value = passwordState.value,
                onValueChange = { passwordState.value = it },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            Button(
                onClick = {
                    val user = login(emailState.value, passwordState.value)
                    if (user != null) {
                        updatedOnLoggedIn.value(user)
                        navController.navigate("packages") // Updated route
                    } else {
                        showSnackBar.value = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text("Login", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Don't have an account? Sign Up",
                color = Color.Black,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable(onClick = {
                    navController.navigate("sign_up") // Navigate to the sign up screen
                })
            )

        }

        if (showSnackBar.value) {
            Snackbar(
                modifier = Modifier.align(Alignment.BottomCenter).padding(8.dp),
                content = { Text("Login failed!") }
            )
        }
    }
}
