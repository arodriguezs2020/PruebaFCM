package es.alvarorodriguez.pruebafcm.ui.auth

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import es.alvarorodriguez.pruebafcm.R
import es.alvarorodriguez.pruebafcm.core.Result
import es.alvarorodriguez.pruebafcm.data.remote.auth.AuthDataSource
import es.alvarorodriguez.pruebafcm.databinding.FragmentLoginBinding
import es.alvarorodriguez.pruebafcm.domain.auth.AuthRepoImpl
import es.alvarorodriguez.pruebafcm.presentation.auth.AuthViewModel
import es.alvarorodriguez.pruebafcm.presentation.auth.AuthViewModelFactory

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val viewModel by viewModels<AuthViewModel> { AuthViewModelFactory(AuthRepoImpl(
        AuthDataSource()
    )) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        isUserLoggedIn()
        doLogin()
    }

    // Comprobamos si el usuario esta logueado ya o no
    private fun isUserLoggedIn() {
        firebaseAuth.currentUser?.let {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }

    // Sino entra a en esta funcion y hará el login
    private fun doLogin() {
        binding.btnSignin.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            validateCredentials(email, password)
            signIn(email, password)
        }
    }

    private fun validateCredentials(email: String, password: String) {

        if (email.isEmpty()) {
            binding.editTextEmail.error = "E-mail is empty"
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editTextEmail.error = "E-mail isn't correct"
            return
        }

        if (password.isEmpty()) {
            binding.editTextPassword.error = "Password is empty"
            return
        }
    }

    private fun signIn(email: String, password: String) {
        viewModel.signIn(email, password).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnSignin.isEnabled = false
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }
                is Result.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnSignin.isEnabled = true
                    Toast.makeText(
                        requireContext(),
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}