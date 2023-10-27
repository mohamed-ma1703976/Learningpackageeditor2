package com.example.learningpackageeditor.model

data class Word(
    val text: String,
    var definitions: List<Definition>,
    var sentences: List<Sentence>
)