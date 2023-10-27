package com.example.learningpackageeditor.model

data class Package(
    val packageId: Int,
    val author: String,
    val category: String,
    val description: String,
    val iconUrl: String,
    val language: String,
    val lastUpdatedDate: String,
    val level: String,
    val title: String,
    val version: Int,
    var words: List<Word>
)