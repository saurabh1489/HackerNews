package com.sample.userstory.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.ActivityCompat.recreate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.sample.userstory.R
import com.sample.userstory.data.repository.Theme
import com.sample.userstory.databinding.FragmentLoginBinding
import com.sample.userstory.ui.common.BaseFragment
import com.sample.userstory.ui.common.ThemeViewModel
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment() {

    private val loginViewModel: LoginViewModel by viewModels {
        viewModelFactory
    }

    private val themeViewModel: ThemeViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this
        binding.loginViewModel = loginViewModel
        observeValidationStatus()
        observeLoginStatus()
        login.setOnClickListener {
            val userName = userName.text.toString()
            val password = password.text.toString()
            loginViewModel.login(userName, password)
        }
        val currentTheme = themeViewModel.getTheme()
        if (currentTheme == Theme.RED) {
            theme.isChecked = true
        }
        theme.setOnCheckedChangeListener { buttonView, isChecked ->
            themeViewModel.setTheme(isChecked)
        }
    }

    private fun observeValidationStatus() {
        loginViewModel.mUsernamePasswordMediator.observe(
            viewLifecycleOwner,
            Observer { validationResult ->
                login.isEnabled = validationResult
            })
    }

    private fun observeLoginStatus() {
        loginViewModel.loginStatus.observe(viewLifecycleOwner, Observer { status ->
            if (status) {
                Toast.makeText(context, "Login Successful!", Toast.LENGTH_LONG).show()
                navigateToDashboard()
                hideKeyboard(login)
            } else {
                Toast.makeText(context, "Login failed. Please try again later!", Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    private fun navigateToDashboard() {
        findNavController().navigate(LoginFragmentDirections.actionLoginToDashboard())
    }

    private fun hideKeyboard(view: View) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}