package com.example.learningpackageeditor.View.ListPackages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.learningpackageeditor.ViewModel.PackageViewModel
import com.example.learningpackageeditor.model.Package
import androidx.compose.runtime.Composable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPackageScreen(
    navController: NavController,
    packageViewModel: PackageViewModel
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var language by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var level by remember { mutableStateOf("") }
    var iconUrl by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Add New Package",
            fontSize = 24.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = author,
            onValueChange = { author = it },
            label = { Text("Author") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = language,
            onValueChange = { language = it },
            label = { Text("Language") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Category") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = level,
            onValueChange = { level = it },
            label = { Text("Level") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = iconUrl,
            onValueChange = { iconUrl = it },
            label = { Text("Icon URL") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                onAddPackageClicked(
                    title,
                    description,
                    author,
                    language,
                    category,
                    level,
                    iconUrl,
                    packageViewModel,
                    navController
                )
            })
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                onAddPackageClicked(
                    title,
                    description,
                    author,
                    language,
                    category,
                    level,
                    iconUrl,
                    packageViewModel,
                    navController
                )
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Add Package")
        }
    }
}

private fun onAddPackageClicked(
    title: String,
    description: String,
    author: String,
    language: String,
    category: String,
    level: String,
    iconUrl: String,
    packageViewModel: PackageViewModel,
    navController: NavController
) {
    if (title.isEmpty() || description.isEmpty() || author.isEmpty() || language.isEmpty() || category.isEmpty() || level.isEmpty() || iconUrl.isEmpty()) {
        // Show error message
        return
    }
    val newPackage = Package(
        packageId = System.currentTimeMillis().toInt(),
        title = title,
        description = description,
        author = author,
        language = language,
        category = category,
        level = level,
        iconUrl = iconUrl,
        version = 1,
        lastUpdatedDate = System.currentTimeMillis().toString(),
        words = emptyList()
    )
    packageViewModel.addPackage(newPackage)
    navController.navigateUp()
}
