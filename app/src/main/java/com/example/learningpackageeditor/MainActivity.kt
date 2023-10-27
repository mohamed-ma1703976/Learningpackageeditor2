package com.example.learningpackageeditor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.learningpackageeditor.View.AddWordScreen
import com.example.learningpackageeditor.View.ListPackages.AddPackageScreen
import com.example.learningpackageeditor.View.ListPackages.Components.AddDefinitionScreen
import com.example.learningpackageeditor.View.ListPackages.Components.AddSentenceScreen
import com.example.learningpackageeditor.View.ListPackages.PackageListScreen
import com.example.learningpackageeditor.View.ListPackages.PackageScreen
import com.example.learningpackageeditor.View.ListPackages.UpdatePackageScreen
import com.example.learningpackageeditor.View.LoginScreen
import com.example.learningpackageeditor.View.SignUpScreen
import com.example.learningpackageeditor.View.UpdateWordScreen
import com.example.learningpackageeditor.ViewModel.PackageViewModel
import com.example.learningpackageeditor.ViewModel.UserViewModel
import com.example.learningpackageeditor.ui.theme.LearningPackageEditorTheme

class MainActivity : ComponentActivity() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var packageViewModel: PackageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewModels
        userViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))
            .get(UserViewModel::class.java)
        packageViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))
            .get(PackageViewModel::class.java)

        setContent {
            LearningPackageEditorTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "login") {
                    composable(route = "login") {
                        LoginScreen(navController, { email, password ->
                            userViewModel.login(email, password)
                        }) { user ->
                            navController.navigate("packages")
                        }
                    }
                    composable(route = "sign_up") {
                        SignUpScreen(userViewModel) { success ->
                            if (success) {
                                navController.navigate("packages")
                            }
                        }
                    }
                    composable(route = "packages") {
                        // Get user data from the UserViewModel
                        val userEmail = userViewModel.user.value?.email ?: ""
                        val userRole = userViewModel.user.value?.role ?: ""

                        // Pass user data to the PackageListScreen composable
                        PackageListScreen(
                            viewModel = packageViewModel,
                            navController = navController,
                            userEmail = userEmail,
                            userRole = userRole
                        )
                    }
                    composable(route = "addPackage") {
                        AddPackageScreen(navController, packageViewModel)
                    }
                    composable(route = "update_package/{packageId}") { backStackEntry ->
                        val packageId = backStackEntry.arguments?.getString("packageId")?.toIntOrNull()
                            ?: return@composable
                        UpdatePackageScreen(
                            navController = navController,
                            packageViewModel = packageViewModel,
                            packageId = packageId
                        )
                    }
                    composable(
                        route = "package_screen/{packageId}",
                        arguments = listOf(navArgument("packageId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val packageId = backStackEntry.arguments?.getInt("packageId")
                        if (packageId != null) {
                            // Get user data from the UserViewModel
                            val userRole = userViewModel.user.value?.role ?: ""
                            val currentUser = userViewModel.user.value?.email ?: "" // Assuming email represents the user's unique identifier

                            // Pass user data to the PackageScreen composable
                            PackageScreen(
                                navController = navController,
                                packageId = packageId,
                                packageViewModel = packageViewModel,
                                userRole = userRole,
                                currentUser = currentUser
                            )
                        }
                    }
                    composable(
                        route = "add_sentence_screen/{packageId}/{wordText}",
                        arguments = listOf(
                            navArgument("packageId") { type = NavType.IntType },
                            navArgument("wordText") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val packageId = backStackEntry.arguments?.getInt("packageId")
                        val wordText = backStackEntry.arguments?.getString("wordText")
                        if (packageId != null && wordText != null) {
                            AddSentenceScreen(
                                navController = navController,
                                packageId = packageId,
                                wordText = wordText,
                                packageViewModel = packageViewModel
                            )
                        }
                    }
                    composable(
                        route = "add_definition_screen/{packageId}/{wordText}",
                        arguments = listOf(
                            navArgument("packageId") { type = NavType.IntType },
                            navArgument("wordText") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val packageId = backStackEntry.arguments?.getInt("packageId")
                        val wordText = backStackEntry.arguments?.getString("wordText")
                        if (packageId != null && wordText != null) {
                            AddDefinitionScreen(
                                navController = navController,
                                packageId = packageId,
                                wordText = wordText,
                                packageViewModel = packageViewModel
                            )
                        }
                    }
                    // MainActivity.kt

                    composable(
                        "add_word_screen/{packageId}",
                        arguments = listOf(
                            navArgument("packageId") { type = NavType.IntType }
                        )
                    ) { backStackEntry ->
                        val packageId = backStackEntry.arguments?.getInt("packageId")
                        if (packageId != null) {
                            AddWordScreen(
                                navController = navController,
                                packageId = packageId,
                                packageViewModel = packageViewModel
                            )
                        }
                    }
// MainActivity.kt
                    composable(
                        route = "update_word_screen/{packageId}/{wordText}",
                        arguments = listOf(
                            navArgument("packageId") { type = NavType.IntType },
                            navArgument("wordText") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val packageId = backStackEntry.arguments?.getInt("packageId")
                        val wordText = backStackEntry.arguments?.getString("wordText")
                        if (packageId != null && wordText != null) {
                            val word = packageViewModel.getWord(packageId, wordText)
                            if (word != null) {
                                UpdateWordScreen(
                                    navController = navController,
                                    packageId = packageId,
                                    packageViewModel = packageViewModel,
                                    oldWord = word
                                )
                            }
                        }
                    }


                }
            }
        }
    }
}
