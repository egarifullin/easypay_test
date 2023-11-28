package ru.evgendev.easypaytest.ui.auth

import android.content.pm.PackageInfo
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import ru.evgendev.easypaytest.MainActivity
import ru.evgendev.easypaytest.R
import ru.evgendev.easypaytest.databinding.FragmentAuthBinding

class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    private lateinit var authViewModel: AuthViewModel
    private var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = NavHostFragment.findNavController(this)
        setupView()
        authViewModel.toastMsg.observe(viewLifecycleOwner, Observer {
            if (it == "") {
                navController?.navigate(R.id.navigation_payments)
            } else {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupView() {
        binding.btnlogin.setOnClickListener(View.OnClickListener {
            MainActivity.hideKeyboardFrom(requireContext(), binding.tietUserName)
            if (!isErrorInEnterSymbol()) {
                authViewModel.login(
                    binding.tietUserName.text.toString(),
                    binding.tietPassword.text.toString()
                )
            }
        })
        resetErrors()
    }

    private fun isErrorInEnterSymbol(): Boolean {
        val userNameText = binding.tietUserName.text.toString()
        val passwordText = binding.tietPassword.text.toString()
        var isError = false

        if (userNameText.isEmpty()) {
            binding.tilUserName.error = "Это поле должно быть заполнено!"
            binding.tilUserName.boxBackgroundColor = resources.getColor(R.color.red_light)
            binding.tilUserName.boxBackgroundColor = resources.getColor(R.color.transparent)
            isError = true
        }
        if (passwordText.isEmpty()) {
            binding.tilPassword.error = "Это поле должно быть заполнено!"
            binding.tilPassword.boxBackgroundColor = resources.getColor(R.color.red_light)
            binding.tilPassword.boxBackgroundColor = resources.getColor(R.color.transparent)
            isError = true
        }
        return isError
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun resetErrors() {
        binding.tietUserName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.tilUserName.error = null
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
        binding.tietPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.tilPassword.error = null
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
    }
}