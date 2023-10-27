package com.example.learningpackageeditor.View.ListPackages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.example.learningpackageeditor.ViewModel.PackageViewModel
import com.example.learningpackageeditor.model.Definition
import com.example.learningpackageeditor.model.Resource
import com.example.learningpackageeditor.model.Sentence
import com.example.learningpackageeditor.model.Word

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PackageScreen(
    navController: NavController,
    packageId: Int,
    packageViewModel: PackageViewModel,
    userRole: String,
    currentUser: String
) {
    val allPackages by packageViewModel.packages.observeAsState(initial = emptyList())

    val pack = allPackages.firstOrNull { it.packageId == packageId }
    if (pack != null) {
        Scaffold(
            floatingActionButton = {
                if (userRole == "Teacher" && currentUser == pack.author) {
                    FloatingActionButton(
                        onClick = {
                            // Handle on click of FloatingActionButton here
                            // For example, navigate to add word screen
                            navController.navigate("add_word_screen/$packageId")
                        }
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues) // Use the paddingValues provided by Scaffold
                    .padding(16.dp) // Additional padding
            ) {
                Text(text = pack.title, fontSize = 24.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = pack.description)
                Spacer(modifier = Modifier.height(16.dp))
                WordsList(
                    words = pack.words,
                    packageId = packageId,
                    packageViewModel = packageViewModel,
                    navController = navController,
                    userRole = userRole,
                    packageAuthor = pack.author,
                    currentUser = currentUser
                )
            }
        }
    } else {
        Text("Package not found")
    }
}

@Composable
fun WordsList(
    words: List<Word>,
    packageId: Int,
    packageViewModel: PackageViewModel,
    navController: NavController,
    userRole: String,
    packageAuthor: String,
    currentUser: String
) {
    LazyColumn {
        items(words) { word ->
            WordItem(
                word = word,
                packageId = packageId,
                packageViewModel = packageViewModel,
                navController = navController,
                userRole = userRole,
                packageAuthor = packageAuthor,
                currentUser = currentUser
            )
        }
    }
}

@Composable
fun WordItem(
    word: Word,
    packageId: Int,
    packageViewModel: PackageViewModel,
    navController: NavController,
    userRole: String,
    packageAuthor: String,
    currentUser: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = word.text, fontSize = 20.sp)

                if (userRole == "Teacher" && packageAuthor == currentUser) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Update Word",
                            modifier = Modifier
                                .clickable {
                                    navController.navigate("update_word_screen/$packageId/${word.text}")
                                }
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Word",
                            modifier = Modifier
                                .clickable {
                                    packageViewModel.deleteWord(packageId,word.text)
                                }
                        )
                    }
                }
            }

            DefinitionsList(definitions = word.definitions)
            Spacer(modifier = Modifier.height(8.dp))
            SentencesList(sentences = word.sentences)
            Spacer(modifier = Modifier.height(8.dp))

            if (userRole == "Teacher" && packageAuthor == currentUser) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            navController.navigate("add_sentence_screen/${packageId}/${word.text}")
                        }
                    ) {
                        Text(text = "Add Sentence")
                    }
                    Button(
                        onClick = {
                            navController.navigate("add_definition_screen/$packageId/${word.text}")
                        }
                    ) {
                        Text(text = "Add Definition")
                    }
                }
            }
        }
    }
}




@Composable
fun DefinitionsList(definitions: List<Definition>) {
    Column {
        definitions.forEach { definition ->
            DefinitionItem(definition = definition)
        }
    }
}

@Composable
fun DefinitionItem(definition: Definition) {
    Text(text = definition.text)
    Text(text = "Source: ${definition.source}")
}

@Composable
fun SentencesList(sentences: List<Sentence>) {
    Column {
        sentences.forEach { sentence ->
            SentenceItem(sentence = sentence)
        }
    }
}

@Composable
fun SentenceItem(sentence: Sentence) {
    Text(text = sentence.text)
    ResourcesList(resources = sentence.resources)
}

@Composable
fun ResourcesList(resources: List<Resource>) {
    Column {
        resources.forEach { resource ->
            ResourceItem(resource = resource)
        }
    }
}

@Composable
fun ResourceItem(resource: Resource) {
    Text(text = resource.title)
    Text(text = "URL: ${resource.url}")
    Text(text = "Type: ${resource.type}")
}
