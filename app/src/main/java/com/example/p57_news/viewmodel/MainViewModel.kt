package com.example.p57_news.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.p57_news.model.Article
import com.example.p57_news.repository.Repository
import com.example.p57_news.util.AppConstants
import com.example.p57_news.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {
    private val _currentTopHeadlines = MutableLiveData<Resource>()
    val currentTopHeadlines: LiveData<Resource> get() = _currentTopHeadlines

    private val _currentEverything = MutableLiveData<Resource>()
    val currentEverything: LiveData<Resource> get() = _currentEverything

    private val _currentArticle = MutableLiveData<Article>()
    val currentArticle: LiveData<Article> get() = _currentArticle

    private val _currentCategory = MutableLiveData(AppConstants.GENERAL)
    val currentCategory: LiveData<String> get() = _currentCategory

    fun setCurrentCategory(category: String) {
        _currentCategory.value = category
    }

    fun setCurrentArticle(article: Article) {
        _currentArticle.value = article
    }

    fun insertArticle(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertArticle(article)
    }

    fun deleteArticle(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteArticle(article)
    }

    fun readArticles() = repository.readArticles()

    fun getTopHeadlines(category: String) = viewModelScope.launch(Dispatchers.IO) {
        _currentTopHeadlines.postValue(Resource.Loading)
        try {
            val response = repository.getTopHeadlines(category)
            _currentTopHeadlines.postValue(Resource.Success(response))
        } catch (exception: Exception) {
            exception.message?.let {
                _currentTopHeadlines.postValue(Resource.Error(it))
            }
        }
    }

    fun getEverything(query: String) = viewModelScope.launch(Dispatchers.IO) {
        _currentEverything.postValue(Resource.Loading)
        try {
            val response = repository.getEverything(query)
            _currentEverything.postValue(Resource.Success(response))
        } catch (exception: Exception) {
            exception.message?.let {
                _currentEverything.postValue(Resource.Error(it))
            }
        }
    }

}












