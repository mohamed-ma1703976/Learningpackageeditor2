package com.example.learningpackageeditor.View

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun UpdateWordScreen(
    navController: NavController,
    packageId: Int,
    packageViewModel: PackageViewModel,
    oldWord: Word
) {
    var wordText by remember { mutableStateOf(oldWord.text) }
    var definitionText by remember { mutableStateOf(oldWord.definitions[0].text) }
    var definitionSource by remember { mutableStateOf(oldWord.definitions[0].source) }
    var sentenceText by remember { mutableStateOf(oldWord.sentences[0].text) }
    var resourceTitle by remember { mutableStateOf(oldWord.sentences[0].resources[0].title) }
    var resourceURL by remember { mutableStateOf(oldWord.sentences[0].resources[0].url) }
    var resourceType by remember { mutableStateOf(oldWord.sentences[0].resources[0].type) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())  // Added scrollable modifier
    ) {
        Text(text = "Update Word", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        // Word TextField
        OutlinedTextField(
            value = wordText,
            onValueChange = { wordText = it },
            label = { Text("Word") }
        )

        // Definition TextField
        OutlinedTextField(
            value = definitionText,
            onValueChange = { definitionText = it },
            label = { Text("Definition") }
        )

        // Definition Source TextField
        OutlinedTextField(
            value = definitionSource,
            onValueChange = { definitionSource = it },
            label = { Text("Source") }
        )

        // Sentence TextField
        OutlinedTextField(
            value = sentenceText,
            onValueChange = { sentenceText = it },
            label = { Text("Sentence") }
        )

        // Resource Title TextField
        OutlinedTextField(
            value = resourceTitle,
            onValueChange = { resourceTitle = it },
            label = { Text("Resource Title") }
        )

        // Resource URL TextField
        OutlinedTextField(
            value = resourceURL,
            onValueChange = { resourceURL = it },
            label = { Text("Resource URL") }
        )

        // Resource Type TextField
        OutlinedTextField(
            value = resourceType,
            onValueChange = { resourceType = it },
            label = { Text("Resource Type") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Update Word Button
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
                packageViewModel.updateWord(packageId, oldWord, newWord)
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update Word")
        }
    }
}
