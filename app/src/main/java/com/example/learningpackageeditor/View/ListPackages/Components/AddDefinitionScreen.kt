package com.example.learningpackageeditor.View.ListPackages.Components
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.learningpackageeditor.ViewModel.PackageViewModel
import com.example.learningpackageeditor.model.Definition
import com.example.learningpackageeditor.model.Sentence

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddDefinitionScreen(
    navController: NavController,
    packageId: Int,
    wordText: String,
    packageViewModel: PackageViewModel
) {
    var definitionText by remember { mutableStateOf("") }
    var sourceText by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = definitionText,
            onValueChange = { definitionText = it },
            label = { Text("Definition") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = sourceText,
            onValueChange = { sourceText = it },
            label = { Text("Source") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                if (definitionText.isNotBlank() && sourceText.isNotBlank()) {
                    val definition = Definition(definitionText, sourceText)
                    packageViewModel.addDefinition(packageId, wordText, definition)
                    Toast.makeText(context, "Definition added", Toast.LENGTH_SHORT).show()
                    navController.navigateUp() // Navigate back to the previous screen
                }
            })
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (definitionText.isNotBlank() && sourceText.isNotBlank()) {
                    val definition = Definition(definitionText, sourceText)
                    packageViewModel.addDefinition(packageId, wordText, definition)
                    Toast.makeText(context, "Definition added", Toast.LENGTH_SHORT).show()
                    navController.navigateUp() // Navigate back to the previous screen
                }
            }
        ) {
            Text("Add")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                navController.navigateUp() // Navigate back to the previous screen
            }
        ) {
            Text("Cancel")
        }
    }
}
