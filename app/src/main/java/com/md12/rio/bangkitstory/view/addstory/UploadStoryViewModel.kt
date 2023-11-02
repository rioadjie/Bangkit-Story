package com.md12.rio.bangkitstory.view.addstory

import androidx.lifecycle.ViewModel
import com.md12.rio.bangkitstory.data.DataRepository
import java.io.File

class UploadStoryViewModel constructor(private val dataRepository: DataRepository) : ViewModel() {

    suspend fun uploadStory(auth: String, description: String, file: File) =
        dataRepository.uploadStory(auth, description, file)
}