package com.md12.rio.bangkitstory.view.login

import androidx.lifecycle.ViewModel
import com.md12.rio.bangkitstory.data.DataRepository

class LoginAndRegisterViewModel constructor(private val dataRepository: DataRepository) : ViewModel() {

    suspend fun register(name: String, email: String, password: String) = dataRepository.register(name, email, password)

    suspend fun login(email: String, password: String) = dataRepository.login(email, password)

}