package ru.evgendev.easypaytest.data.network.model

data class PaymentDto(
    val id: Int,
    val title: Any?,
    val amount: Any?,
    val created: Long?
)