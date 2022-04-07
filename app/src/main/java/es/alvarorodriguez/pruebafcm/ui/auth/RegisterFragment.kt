package es.alvarorodriguez.pruebafcm.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import es.alvarorodriguez.pruebafcm.R
import es.alvarorodriguez.pruebafcm.core.Result
import es.alvarorodriguez.pruebafcm.data.remote.auth.AuthDataSource
import es.alvarorodriguez.pruebafcm.databinding.FragmentRegisterBinding
import es.alvarorodriguez.pruebafcm.domain.auth.AuthRepoImpl
import es.alvarorodriguez.pruebafcm.presentation.auth.AuthViewModel
import es.alvarorodriguez.pruebafcm.presentation.auth.AuthViewModelFactory

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding

    private val viewModel by viewModels<AuthViewModel> { AuthViewModelFactory(
        AuthRepoImpl(AuthDataSource())
    )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        signUp()
    }

    private fun signUp() {
        binding.btnSignup.setOnClickListener {

            val username = binding.editTextUsername.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            val confirmPassword = binding.editTextConfirmPassword.text.toString().trim()
            val tel = binding.editTextTel.text.toString().trim()

            if (validateRegister(password, confirmPassword, username, email, tel)) return@setOnClickListener

            createUser(username, email, password, tel)

        }
    }

    private fun createUser(username: String, email: String, password: String, tel: String) {
        viewModel.signUp(username, email, password, tel).observe(viewLifecycleOwner) { result ->
            when(result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnSignup.isEnabled = true
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnSignup.isEnabled = false
                    findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
                }
                is Result.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnSignup.isEnabled = true
                    Toast.makeText(
                        requireContext(),
                        "Error ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun validateRegister(
        password: String,
        confirmPassword: String,
        username: String,
        email: String,
        tel: String
    ) : Boolean {
        if (password != confirmPassword) {
            binding.editTextConfirmPassword.error = "Password dows not match"
            binding.editTextPassword.error = "Password does not match"
            return true
        }

        if (username.isEmpty()) {
            binding.editTextUsername.error = "Username is empty"
            return true
        }

        if (email.isEmpty()) {
            binding.editTextEmail.error = "Email is empty"
            return true
        }

        if (tel.isEmpty()) {
            binding.editTextTel.error = "Phone is empty"
            return true
        }

        return false
    }
}