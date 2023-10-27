package com.example.learningpackageeditor.View.ListPackages

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.learningpackageeditor.ViewModel.PackageViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.navigation.NavController
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PackageListScreen(
    viewModel: PackageViewModel,
    navController: NavController,
    userEmail: String,
    userRole: String
) {
    var searchText by remember { mutableStateOf("") }
    val packageList by viewModel.packages.observeAsState(listOf())
    val searchResults by viewModel.searchResults.observeAsState(listOf())

    // Determine which list to display
    val displayList = if (searchText.isBlank()) packageList else searchResults

    Scaffold(
        floatingActionButton = {
            if (userRole == "Teacher") {
                FloatingActionButton(onClick = {
                    // Handle add package action
                    navController.navigate("addPackage")  // update the route as per your navigation graph
                }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Package")
                }
            }
        }
    ) {
        Column {
            TextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                    if (it.isNotBlank()) {
                        viewModel.searchPackages(it)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                label = { Text("Search") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if (searchText.isNotBlank()) {
                            viewModel.searchPackages(searchText)
                        }
                    }
                ),
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(displayList) { packageItem ->
                    PackageCard(
                        pack = packageItem,
                        packageViewModel = viewModel,
                        navController = navController,
                        userEmail = userEmail,
                        userRole = userRole,
                        onClick = { packageId ->
                            // Navigate to the PackageScreen with the correct package ID
                            navController.navigate("package_screen/$packageId")
                        }
                    )
                }
            }
        }
    }
}