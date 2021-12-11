package com.salmanseifian.android.architecture.sample.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.salmanseifian.android.architecture.sample.data.model.Item
import com.salmanseifian.android.architecture.sample.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class RepositoriesViewModel(private val repository: Repository) : ViewModel() {

    private val _allRepositories = MutableLiveData<PagingData<Item>>()
    val allRepositories: LiveData<PagingData<Item>>
        get() = _allRepositories

    init {
        searchRepositories("tris")
    }

    private fun searchRepositories(q: String) {
        viewModelScope.launch {
            repository.searchRepositories(q)
                .cachedIn(viewModelScope)
                .collectLatest {
                    _allRepositories.postValue(it)
                }
        }
    }
}