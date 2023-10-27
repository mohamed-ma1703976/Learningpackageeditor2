package com.example.learningpackageeditor.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.learningpackageeditor.Repository.PackageRepository
import com.example.learningpackageeditor.model.Definition
import com.example.learningpackageeditor.model.Package
import com.example.learningpackageeditor.model.Sentence
import com.example.learningpackageeditor.model.Word

class PackageViewModel(application: Application) : AndroidViewModel(application) {

    private val packageRepository = PackageRepository(application.applicationContext)

    private val _packages = MutableLiveData<List<Package>>()
    val packages: LiveData<List<Package>> = _packages

    private val _searchResults = MutableLiveData<List<Package>>()
    val searchResults: LiveData<List<Package>> = _searchResults

    init {
        getAllPackages()
    }

    fun getAllPackages() {
        val allPackages = packageRepository.getAllPackages()
        _packages.postValue(allPackages)
    }

    fun searchPackages(query: String) {
        val results = packageRepository.searchPackages(query)
        _searchResults.postValue(results)
    }

    fun addPackage(pack: Package) {
        packageRepository.addPackage(
            packageId = pack.packageId,
            author = pack.author,
            category = pack.category,
            description = pack.description,
            iconUrl = pack.iconUrl,
            language = pack.language,
            lastUpdatedDate = pack.lastUpdatedDate,
            level = pack.level,
            title = pack.title,
            version = pack.version,
            words = pack.words
        )
        savePackages()
        getAllPackages()
    }

    fun updatePackage(pack: Package) {
        packageRepository.updatePackage(
            packageId = pack.packageId,
            author = pack.author,
            category = pack.category,
            description = pack.description,
            iconUrl = pack.iconUrl,
            language = pack.language,
            lastUpdatedDate = pack.lastUpdatedDate,
            level = pack.level,
            title = pack.title,
            version = pack.version,
            words = pack.words
        )
        savePackages()
        getAllPackages()
    }


    fun deletePackage(packageId: Int) {
        packageRepository.deletePackage(packageId)
        savePackages()
        getAllPackages()
    }

    fun savePackages() {
        packageRepository.savePackagesToJson()
    }
    fun addWord(packageId: Int, word: Word) {
        packageRepository.addWord(packageId, word)
        savePackages()
        getAllPackages()
    }

    fun updateWord(packageId: Int, oldWord: Word, newWord: Word) {
        packageRepository.updateWord(packageId, oldWord, newWord)
        savePackages()
        getAllPackages()
    }

    fun deleteWord(packageId: Int, wordText: String) {
        packageRepository.deleteWord(packageId, wordText)
        savePackages()
        getAllPackages()
    }

    fun addSentence(packageId: Int, wordText: String, sentence: Sentence) {
        packageRepository.addSentence(packageId, wordText, sentence)
        savePackages()
        getAllPackages()
    }

    fun updateSentence(packageId: Int, wordText: String, oldSentence: Sentence, newSentence: Sentence) {
        packageRepository.updateSentence(packageId, wordText, oldSentence, newSentence)
        savePackages()
        getAllPackages()
    }

    fun deleteSentence(packageId: Int, wordText: String, sentenceText: String) {
        packageRepository.deleteSentence(packageId, wordText, sentenceText)
        savePackages()
        getAllPackages()
    }

    fun addDefinition(packageId: Int, wordText: String, definition: Definition) {
        packageRepository.addDefinition(packageId, wordText, definition)
        savePackages()
        getAllPackages()
    }

    fun updateDefinition(packageId: Int, wordText: String, oldDefinition: Definition, newDefinition: Definition) {
        packageRepository.updateDefinition(packageId, wordText, oldDefinition, newDefinition)
        savePackages()
        getAllPackages()
    }

    fun deleteDefinition(packageId: Int, wordText: String, definitionText: String, source: String) {
        packageRepository.deleteDefinition(packageId, wordText, definitionText, source)
        savePackages()
        getAllPackages()
    }
    fun getWord(packageId: Int, wordText: String): Word? {
        val pack = _packages.value?.find { it.packageId == packageId }
        return pack?.words?.find { it.text == wordText }
    }

}
