package com.example.learningpackageeditor.View

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.learningpackageeditor.ViewModel.PackageViewModel
import com.example.learningpackageeditor.model.Definition
import com.example.learningpackageeditor.model.Sentence
import com.example.learningpackageeditor.model.Word

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWordScreen(
    navController: NavController,
    packageId: Int,
    packageViewModel: PackageViewModel
) {
    var wordText by remember { mutableStateOf("") }
    var definitionText by remember { mutableStateOf("") }
    var definitionSource by remember { mutableStateOf("") }
    var sentenceText by remember { mutableStateOf("") }
    var resourceTitle by remember { mutableStateOf("") }
    var resourceURL by remember { mutableStateOf("") }
    var resourceType by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Add Word", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = wordText,
            onValueChange = { wordText = it },
            label = { Text("Word") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = definitionText,
            onValueChange = { definitionText = it },
            label = { Text("Definition") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = definitionSource,
            onValueChange = { definitionSource = it },
            label = { Text("Source") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = sentenceText,
            onValueChange = { sentenceText = it },
            label = { Text("Sentence") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = resourceTitle,
            onValueChange = { resourceTitle = it },
            label = { Text("Resource Title") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = resourceURL,
            onValueChange = { resourceURL = it },
            label = { Text("Resource URL") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = resourceType,
            onValueChange = { resourceType = it },
            label = { Text("Resource Type") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val newWord = Word(
                    text = wordText,
                    definitions = listOf(
                        Definition(
                            text = definitionText,
                            source = definitionSource
                        )
                    ),
                    sentences = listOf(
                        Sentence(
                            text = sentenceText,
                            resources = listOf(
                                com.example.learningpackageeditor.model.Resource(
                                    title = resourceTitle,
                                    url = resourceURL,
                                    type = resourceType
                                )
                            )
                        )
                    )
                )
                packageViewModel.addWord(packageId, newWord)
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Word")
        }
    }
}
