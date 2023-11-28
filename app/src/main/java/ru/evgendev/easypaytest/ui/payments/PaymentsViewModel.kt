package ru.evgendev.easypaytest.ui.payments

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.evgendev.easypaytest.R
import ru.evgendev.easypaytest.data.network.model.PaymentDto
import ru.evgendev.easypaytest.data.network.repository.RepositoryImpl

class PaymentsViewModel : ViewModel() {

    private val repository = RepositoryImpl()

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg: MutableLiveData<String> = _errorMsg

    private var _payments = MutableLiveData<List<PaymentDto>?>()
    val payments: MutableLiveData<List<PaymentDto>?> = _payments

    fun getPayments(context: Context, token: String) {
        viewModelScope.launch {
            try {
                val response = repository.getPayments(token)
                if (response?.isSuccessful == true) {
                    if (response.body()?.success.equals("true")) {
                        Log.i("token", response.body()?.response?.size.toString())
                        _payments.value = response.body()?.response
                        _errorMsg.value = ""
                    } else {
                        _errorMsg.value = context.resources.getString(R.string.error_load_payments)
                    }
                } else {
                    _errorMsg.value = context.resources.getString(R.string.error_load_payments)
                }
            } catch (e: Exception) {
                _errorMsg.value = context.resources.getString(R.string.error_load_payments)
            }
        }
    }
}