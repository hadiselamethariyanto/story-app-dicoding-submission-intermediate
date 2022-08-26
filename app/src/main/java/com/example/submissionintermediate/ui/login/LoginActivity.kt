package com.example.submissionintermediate.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.submissionintermediate.R
import com.example.submissionintermediate.data.mechanism.Resource
import com.example.submissionintermediate.data.remote.response.LoginResponse
import com.example.submissionintermediate.databinding.ActivityLoginBinding
import com.example.submissionintermediate.ui.base.BaseActivity
import com.example.submissionintermediate.ui.main.MainActivity
import com.example.submissionintermediate.ui.register.RegisterActivity
import com.example.submissionintermediate.utils.Utils.afterTextChanged
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity() {

    private val viewModel: LoginViewModel by viewModel()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.login)

        val email = binding.etEmail
        val password = binding.etPassword

        viewModel.formState.observe(this) {
            binding.btnLogin.setEnable(it)
        }

        binding.etEmail.afterTextChanged {
            viewModel.dataChanged(
                email.error.isNullOrEmpty() && email.text.toString().isNotEmpty(),
                password.error.isNullOrEmpty() && password.text.toString().isNotEmpty()
            )
        }

        binding.etPassword.afterTextChanged {
            viewModel.dataChanged(
                email.error.isNullOrEmpty() && email.text.toString().isNotEmpty(),
                password.error.isNullOrEmpty() && password.text.toString().isNotEmpty()
            )
        }

        binding.btnLogin.setText(getString(R.string.login))
        binding.btnLogin.setOnClickListener {
            viewModel.login(email.text.toString(), password.text.toString())
                .observe(this, loginObserver)
        }

        binding.tvSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private val loginObserver = Observer<Resource<LoginResponse>> { res ->
        when (res) {
            is Resource.Loading -> {
                binding.btnLogin.setLoading(true)
            }
            is Resource.Success -> {
                binding.btnLogin.setLoading(false)
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            is Resource.Error -> {
                binding.btnLogin.setLoading(false)
                Toast.makeText(this, res.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}