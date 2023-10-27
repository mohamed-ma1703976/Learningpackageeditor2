package com.example.learningpackageeditor.View.ListPackages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.learningpackageeditor.R
import com.example.learningpackageeditor.ViewModel.PackageViewModel
import com.example.learningpackageeditor.model.Package

@Composable
fun PackageCard(
    pack: Package,
    packageViewModel: PackageViewModel,
    navController: NavController,
    userEmail: String,
    userRole: String,
    onClick: (Int) -> Unit  // Added click listener
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(pack.packageId) },  // Added click listener
    ){
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = pack.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                if (userRole == "Teacher" && pack.author == userEmail) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(Color.Gray)
                                .clickable {
                                    navController.navigate("update_package/${pack.packageId}")
                                }
                                .padding(6.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(Color.Gray)
                                .clickable {
                                    packageViewModel.deletePackage(pack.packageId)
                                }
                                .padding(6.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = pack.description, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Author: ${pack.author}")
                    Text(text = "Category: ${pack.category}")
                    Text(text = "Language: ${pack.language}")
                    Text(text = "Level: ${pack.level}")
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Package icon",
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color.Gray)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Last updated: ${pack.lastUpdatedDate}")
            Text(text = "Version: ${pack.version}")
        }
    }
}

