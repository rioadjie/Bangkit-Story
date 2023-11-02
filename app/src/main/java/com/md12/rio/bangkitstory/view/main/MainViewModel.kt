package com.md12.rio.bangkitstory.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.md12.rio.bangkitstory.data.DataRepository
import com.md12.rio.bangkitstory.data.remote.response.story.ResponseStory
import com.md12.rio.bangkitstory.utils.NetworkResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel constructor(private val repository: DataRepository) : ViewModel() {
    private val listStory = MutableLiveData<NetworkResult<ResponseStory>>()
    private var job: Job? = null

    fun fetchListStory(auth: String) {
        job = viewModelScope.launch {
            repository.getStories(auth).collectLatest {
                listStory.value = it
            }
        }
    }

    val responseListStory: LiveData<NetworkResult<ResponseStory>> = listStory


    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}