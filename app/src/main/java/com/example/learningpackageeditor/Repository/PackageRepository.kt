package com.example.learningpackageeditor.Repository

import android.content.Context
import com.example.learningpackageeditor.model.*
import com.google.gson.Gson
import org.json.JSONArray
import java.io.IOException

class PackageRepository(private val context: Context) {

    val packageList = mutableListOf<Package>()

    init {
        initPackages()
    }

    private fun initPackages() {
        val jsonString: String = getJsonDataFromAsset("packages.json") ?: return
        val jsonArray = JSONArray(jsonString)

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val pkg = Package(
                packageId = jsonObject.getInt("packageId"),
                author = jsonObject.getString("author"),
                category = jsonObject.getString("category"),
                description = jsonObject.getString("description"),
                iconUrl = jsonObject.optString("iconUrl", ""),
                language = jsonObject.getString("language"),
                lastUpdatedDate = jsonObject.getString("lastUpdatedDate"),
                level = jsonObject.getString("level"),
                title = jsonObject.getString("title"),
                version = jsonObject.getInt("version"),
                words = jsonObject.getJSONArray("words").let { wordsArray ->
                    List(wordsArray.length()) { j ->
                        val wordObject = wordsArray.getJSONObject(j)
                        Word(
                            text = wordObject.getString("text"),
                            definitions = wordObject.getJSONArray("definitions").let { definitionsArray ->
                                List(definitionsArray.length()) { k ->
                                    val definitionObject = definitionsArray.getJSONObject(k)
                                    Definition(
                                        text = definitionObject.getString("text"),
                                        source = definitionObject.getString("source")
                                    )
                                }
                            },
                            sentences = wordObject.getJSONArray("sentences").let { sentencesArray ->
                                List(sentencesArray.length()) { l ->
                                    val sentenceObject = sentencesArray.getJSONObject(l)
                                    Sentence(
                                        text = sentenceObject.getString("text"),
                                        resources = sentenceObject.getJSONArray("resources").let { resourcesArray ->
                                            List(resourcesArray.length()) { m ->
                                                val resourceObject = resourcesArray.getJSONObject(m)
                                                Resource(
                                                    title = resourceObject.getString("title"),
                                                    url = resourceObject.getString("url"),
                                                    type = resourceObject.getString("type")
                                                )
                                            }
                                        }
                                    )
                                }
                            }
                        )
                    }
                }
            )
            packageList.add(pkg)
        }
    }

    fun searchPackages(query: String): List<Package> {
        return packageList.filter { pkg ->
            pkg.title.contains(query, ignoreCase = true) ||
                    pkg.description.contains(query, ignoreCase = true) ||
                    pkg.words.any { word ->
                        word.text.contains(query, ignoreCase = true) ||
                                word.definitions.any { definition ->
                                    definition.text.contains(query, ignoreCase = true)
                                } ||
                                word.sentences.any { sentence ->
                                    sentence.text.contains(query, ignoreCase = true)
                                }
                    }
        }
    }

    fun getAllPackages(): List<Package> {
        return packageList.toList()
    }

    private fun getJsonDataFromAsset(fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
    fun addPackage(
        packageId: Int,
        author: String,
        category: String,
        description: String,
        iconUrl: String,
        language: String,
        lastUpdatedDate: String,
        level: String,
        title: String,
        version: Int,
        words: List<Word>
    ) {
        val newPackage = Package(
            packageId = packageId,
            author = author,
            category = category,
            description = description,
            iconUrl = iconUrl,
            language = language,
            lastUpdatedDate = lastUpdatedDate,
            level = level,
            title = title,
            version = version,
            words = words
        )
        packageList.add(newPackage)
        savePackagesToJson()
    }

    fun updatePackage(
        packageId: Int,
        author: String,
        category: String,
        description: String,
        iconUrl: String,
        language: String,
        lastUpdatedDate: String,
        level: String,
        title: String,
        version: Int,
        words: List<Word>
    ) {
        val index = packageList.indexOfFirst { it.packageId == packageId }
        if (index != -1) {
            val updatedPackage = Package(
                packageId = packageId,
                author = author,
                category = category,
                description = description,
                iconUrl = iconUrl,
                language = language,
                lastUpdatedDate = lastUpdatedDate,
                level = level,
                title = title,
                version = version,
                words = words
            )
            packageList[index] = updatedPackage
            savePackagesToJson()
        }
    }

    fun deletePackage(packageId: Int) {
        val index = packageList.indexOfFirst { it.packageId == packageId }
        if (index != -1) {
            packageList.removeAt(index)
            savePackagesToJson()
        }
    }

    fun savePackagesToJson() {
        try {
            val jsonString = Gson().toJson(packageList)
            context.openFileOutput("packages.json", Context.MODE_PRIVATE).use {
                it.write(jsonString.toByteArray())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    fun addWord(packageId: Int, word: Word) {
        val pkg = packageList.firstOrNull { it.packageId == packageId }
        if (pkg != null) {
            val newWords = pkg.words.toMutableList()
            newWords.add(word)
            pkg.words = newWords
            savePackagesToJson()
        }
    }

    fun updateWord(packageId: Int, oldWord: Word, newWord: Word) {
        val pkg = packageList.firstOrNull { it.packageId == packageId }
        if (pkg != null) {
            val newWords = pkg.words.map {
                if (it.text == oldWord.text) newWord else it
            }
            pkg.words = newWords
            savePackagesToJson()
        }
    }

    fun deleteWord(packageId: Int, wordText: String) {
        val pkg = packageList.firstOrNull { it.packageId == packageId }
        if (pkg != null) {
            val newWords = pkg.words.filter { it.text != wordText }
            pkg.words = newWords
            savePackagesToJson()
        }
    }

    // Add and update sentence functions
    fun addSentence(packageId: Int, wordText: String, sentence: Sentence) {
        val pkg = packageList.firstOrNull { it.packageId == packageId }
        val word = pkg?.words?.firstOrNull { it.text == wordText }
        if (word != null) {
            val newSentences = word.sentences.toMutableList()
            newSentences.add(sentence)
            word.sentences = newSentences
            savePackagesToJson()
        }
    }

    fun updateSentence(packageId: Int, wordText: String, oldSentence: Sentence, newSentence: Sentence) {
        val pkg = packageList.firstOrNull { it.packageId == packageId }
        val word = pkg?.words?.firstOrNull { it.text == wordText }
        if (word != null) {
            val index = word.sentences.indexOfFirst { it.text == oldSentence.text }
            if (index != -1) {
                val newSentences = word.sentences.toMutableList()
                newSentences[index] = newSentence
                word.sentences = newSentences
                savePackagesToJson()
            }
        }
    }

    fun deleteSentence(packageId: Int, wordText: String, sentenceText: String) {
        val pkg = packageList.firstOrNull { it.packageId == packageId }
        val word = pkg?.words?.firstOrNull { it.text == wordText }
        if (word != null) {
            val index = word.sentences.indexOfFirst { it.text == sentenceText }
            if (index != -1) {
                val newSentences = word.sentences.toMutableList()
                newSentences.removeAt(index)
                word.sentences = newSentences
                savePackagesToJson()
            }
        }
    }

    // Add and update definition functions
    fun addDefinition(packageId: Int, wordText: String, definition: Definition) {
        val pkg = packageList.firstOrNull { it.packageId == packageId }
        val word = pkg?.words?.firstOrNull { it.text == wordText }
        if (word != null) {
            val newDefinitions = word.definitions.toMutableList()
            newDefinitions.add(definition)
            word.definitions = newDefinitions
            savePackagesToJson()
        }
    }

    fun updateDefinition(packageId: Int, wordText: String, oldDefinition: Definition, newDefinition: Definition) {
        val pkg = packageList.firstOrNull { it.packageId == packageId }
        val word = pkg?.words?.firstOrNull { it.text == wordText }
        if (word != null) {
            val index = word.definitions.indexOfFirst { it.text == oldDefinition.text && it.source == oldDefinition.source }
            if (index != -1) {
                val newDefinitions = word.definitions.toMutableList()
                newDefinitions[index] = newDefinition
                word.definitions = newDefinitions
                savePackagesToJson()
            }
        }
    }

    fun deleteDefinition(packageId: Int, wordText: String, definitionText: String, source: String) {
        val pkg = packageList.firstOrNull { it.packageId == packageId }
        val word = pkg?.words?.firstOrNull { it.text == wordText }
        if (word != null) {
            val index = word.definitions.indexOfFirst { it.text == definitionText && it.source == source }
            if (index != -1) {
                val newDefinitions = word.definitions.toMutableList()
                newDefinitions.removeAt(index)
                word.definitions = newDefinitions
                savePackagesToJson()
            }
        }
    }

}
