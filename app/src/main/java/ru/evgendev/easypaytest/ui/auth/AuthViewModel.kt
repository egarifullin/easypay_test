package ru.evgendev.easypaytest.ui.auth

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import ru.evgendev.easypaytest.R
import ru.evgendev.easypaytest.data.network.model.TokenDto
import ru.evgendev.easypaytest.data.network.repository.RepositoryImpl
import ru.evgendev.easypaytest.ui.utils.Utils

class AuthViewModel : ViewModel() {

    private val repository = RepositoryImpl()

    private val _toastMsg = MutableLiveData<String>()
    val toastMsg: MutableLiveData<String> = _toastMsg

    fun login(context: Context, userName: String, password: String) {
        viewModelScope.launch {
            try {
                val paramObject = JsonObject()
                paramObject.addProperty("login", userName)
                paramObject.addProperty("password", password)

                val responseApi = repository.login(paramObject)
                if (responseApi?.isSuccessful == true) {
                    if (responseApi.body()?.success.equals("true")) {
                        val gson = Gson()
                        val tokenResponse = gson.fromJson(
                            responseApi.body()?.response.toString(),
                            TokenDto::class.java
                        )
                        Utils.writeTokenToSharedPref(tokenResponse.token)
                        Log.i("token", tokenResponse.token)
                        _toastMsg.value = ""
                    } else {
                        _toastMsg.value = context.resources.getString(R.string.login_error)
                    }
                } else {
                    _toastMsg.value = context.resources.getString(R.string.login_error)
                }
            } catch (e: Exception) {
                _toastMsg.value = context.resources.getString(R.string.login_error)
            }
        }
    }
}