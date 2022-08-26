package com.example.submissionintermediate.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.submissionintermediate.R
import com.example.submissionintermediate.data.mechanism.Resource
import com.example.submissionintermediate.data.remote.response.RegisterResponse
import com.example.submissionintermediate.databinding.ActivityRegisterBinding
import com.example.submissionintermediate.ui.base.BaseActivity
import com.example.submissionintermediate.ui.login.LoginActivity
import com.example.submissionintermediate.utils.Utils.afterTextChanged
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : BaseActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.sign_up)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fullName = binding.etName
        val email = binding.etEmail
        val password = binding.etPassword

        binding.etName.afterTextChanged {
            viewModel.dataChanged(
                email.text.toString(),
                fullName.text.toString(),
                password.text.toString()
            )
        }

        binding.etEmail.afterTextChanged {
            viewModel.dataChanged(
                email.text.toString(),
                fullName.text.toString(),
                password.text.toString()
            )
        }

        binding.etPassword.afterTextChanged {
            viewModel.dataChanged(
                email.text.toString(),
                fullName.text.toString(),
                password.text.toString()
            )
        }

        binding.btnRegister.apply {
            setText(getString(R.string.sign_up))
        }

        viewModel.formState.observe(this) {
            binding.btnRegister.setEnable(it)
        }

        binding.btnRegister.setOnClickListener {
            viewModel.register(
                fullName.text.toString(),
                email.text.toString(),
                password.text.toString()
            ).observe(this, registerObserver)
        }
    }


    private val registerObserver = Observer<Resource<RegisterResponse>> { res ->
        when (res) {
            is Resource.Loading -> {
                binding.btnRegister.setLoading(true)
            }
            is Resource.Success -> {
                binding.btnRegister.setLoading(false)
                startActivity(Intent(this, LoginActivity::class.java))
            }
            is Resource.Error -> {
                binding.btnRegister.setLoading(false)
                Toast.makeText(this, res.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}