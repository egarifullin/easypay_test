package ru.evgendev.easypaytest.ui.payments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import ru.evgendev.easypaytest.R
import ru.evgendev.easypaytest.data.network.model.PaymentDto
import ru.evgendev.easypaytest.databinding.FragmentAuthBinding
import ru.evgendev.easypaytest.databinding.FragmentPaymentsBinding
import ru.evgendev.easypaytest.ui.auth.AuthViewModel
import ru.evgendev.easypaytest.ui.utils.Utils

class PaymentsFragment : Fragment() {

    private var _binding: FragmentPaymentsBinding? = null
    private val binding get() = _binding!!

    private lateinit var paymentsViewModel: PaymentsViewModel
    private var navController: NavController? = null

    private lateinit var adapter: PaymentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        paymentsViewModel = ViewModelProvider(this)[PaymentsViewModel::class.java]

        _binding = FragmentPaymentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = NavHostFragment.findNavController(this)
        setupView()
        getPayments()
    }

    private fun getPayments() {
        paymentsViewModel.getPayments(requireContext(), Utils.getUserToken())
    }

    private fun setupView() {
        binding.srlPayments.isRefreshing = true
        binding.ivLogout.setOnClickListener(View.OnClickListener {
            Utils.writeTokenToSharedPref("")
            navController?.popBackStack()
            navController?.navigate(R.id.navigation_auth)
        })
        binding.srlPayments.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            getPayments()
        })
        adapter = PaymentAdapter()
        adapter.onPaymentClickListener =
            object : PaymentAdapter.OnPaymentClickListener {
                override fun onPaymentClick(paymentDto: PaymentDto) {
                    Utils.showPaymentDialog(requireContext(), paymentDto, binding.llShadowAuth)
                }
            }
        binding.rvPayments.adapter = adapter
        createObservers()
    }

    private fun createObservers() {
        paymentsViewModel.payments.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            if (it?.size == 0) {
                Toast.makeText(
                    context,
                    resources.getString(R.string.error_load_payments),
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.srlPayments.isRefreshing = false
        })
        paymentsViewModel.errorMsg.observe(viewLifecycleOwner, Observer {
            if (it != "") {
                Toast.makeText(
                    context,
                    resources.getString(R.string.error_load_payments),
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.srlPayments.isRefreshing = false
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}