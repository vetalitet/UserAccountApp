package com.vetalitet.useraccountapp.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.vetalitet.useraccountapp.R
import com.vetalitet.useraccountapp.core.UiState
import com.vetalitet.useraccountapp.core.base.BaseFragment
import com.vetalitet.useraccountapp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadData()
        handleResults()
        handleLoginClick()
    }

    private fun handleLoginClick() {
        binding.buttonLogin.setOnClickListener {
            viewModel.login(binding.etLogin.text.toString(), binding.etPassword.text.toString())
        }
    }

    private fun handleResults() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginFlow.collect { state ->
                    when (state) {
                        is UiState.Success -> {
                            state.result?.let { res ->
                                if (res) {
                                    findNavController().navigate(R.id.action_loginFragment_to_profileFragment)
                                } else {
                                    showError("Invalid login or password")
                                }
                            }
                        }
                        is UiState.Error -> {
                            showError(state.message)
                        }
                        is UiState.Loading -> {
                            // Можна показати progress
                        }
                    }
                }
            }
        }
    }

    private fun loadData() {
        viewModel.loadUser()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            LoginFragment().apply {
                arguments = Bundle()
            }
    }

}
