package ru.evgendev.easypaytest.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.evgendev.easypaytest.data.network.model.ResponseApi
import ru.evgendev.easypaytest.data.network.repository.RepositoryImpl

class AuthViewModel : ViewModel() {

    private val repository = RepositoryImpl()

    private val _authResponse = MutableLiveData<ResponseApi?>()
    val authResponse: MutableLiveData<ResponseApi?> = _authResponse
}