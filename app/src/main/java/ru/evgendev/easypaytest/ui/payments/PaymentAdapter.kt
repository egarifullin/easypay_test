package ru.evgendev.easypaytest.ui.payments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.evgendev.easypaytest.data.network.model.PaymentDto
import ru.evgendev.easypaytest.databinding.ItemPaymentBinding
import ru.evgendev.easypaytest.ui.utils.Utils

class PaymentAdapter :
    ListAdapter<PaymentDto, PaymentAdapter.PaymentViewHolder>(PaymentDiffCallback) {

    var onPaymentClickListener: OnPaymentClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        val binding = ItemPaymentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PaymentViewHolder(binding)
    }


    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        val payment = getItem(position)
        with(holder.binding) {
            with(payment) {
                if (title != null && title != "") {
                    tvTitlePayment.text = title.toString()
                } else {
                    tvTitlePayment.text = "-"
                }
                if (amount != null && amount != "") {
                    tvAmountPayment.text = amount.toString()
                    tvAmountPayment.visibility = View.VISIBLE
                } else {
                    tvAmountPayment.visibility = View.GONE
                }
                if (created != null) {
                    tvDatePayment.text =
                        Utils.getDate(created, "dd.MM.yyyy hh:mm")
                    tvDatePayment.visibility = View.VISIBLE
                } else {
                    tvDatePayment.visibility = View.GONE
                }
                root.setOnClickListener {
                    onPaymentClickListener?.onPaymentClick(this)
                }
            }
        }
    }

    inner class PaymentViewHolder(
        val binding: ItemPaymentBinding
    ) : RecyclerView.ViewHolder(binding.root)

    interface OnPaymentClickListener {
        fun onPaymentClick(paymentDto: PaymentDto)
    }

    object PaymentDiffCallback : DiffUtil.ItemCallback<PaymentDto>() {
        override fun areItemsTheSame(oldItem: PaymentDto, newItem: PaymentDto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PaymentDto, newItem: PaymentDto): Boolean {
            return oldItem == newItem
        }

    }
}