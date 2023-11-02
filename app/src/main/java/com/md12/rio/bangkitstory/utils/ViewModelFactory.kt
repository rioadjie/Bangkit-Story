package com.md12.rio.bangkitstory.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.md12.rio.bangkitstory.data.DataRepository
import com.md12.rio.bangkitstory.view.addstory.UploadStoryViewModel
import com.md12.rio.bangkitstory.view.login.LoginAndRegisterViewModel
import com.md12.rio.bangkitstory.view.main.MainViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(private val dataRepository: DataRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginAndRegisterViewModel::class.java) -> {
                LoginAndRegisterViewModel(dataRepository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(dataRepository) as T
            }
            modelClass.isAssignableFrom(UploadStoryViewModel::class.java) -> {
                UploadStoryViewModel(dataRepository) as T
            }
            else -> {
                throw IllegalArgumentException("Class ViewModel not Implement")
            }
        }
    }

}