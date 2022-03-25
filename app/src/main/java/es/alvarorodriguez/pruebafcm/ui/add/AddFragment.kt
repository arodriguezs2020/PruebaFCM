package es.alvarorodriguez.pruebafcm.ui.add

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import es.alvarorodriguez.pruebafcm.R
import es.alvarorodriguez.pruebafcm.core.Result
import es.alvarorodriguez.pruebafcm.data.remote.add.AddDataSource
import es.alvarorodriguez.pruebafcm.databinding.FragmentAddBinding
import es.alvarorodriguez.pruebafcm.domain.add.AddRepoImpl
import es.alvarorodriguez.pruebafcm.presentation.add.AddViewModel
import es.alvarorodriguez.pruebafcm.presentation.add.AddViewModelFactory

class AddFragment : Fragment(R.layout.fragment_add) {

    private lateinit var binding: FragmentAddBinding

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val viewModel by viewModels<AddViewModel> {
        AddViewModelFactory(
            AddRepoImpl(
                AddDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddBinding.bind(view)
        logout()
        addCar()
    }

    private fun addCar() {
        binding.btnAdd.setOnClickListener {

            val marca = binding.marca.text.toString().trim()
            val description = binding.description.text.toString().trim()

            observeAddCar(marca, description)
        }
    }

    private fun logout() {
        binding.logout.setOnClickListener {
            firebaseAuth.signOut()
            Toast.makeText(
                requireContext(),
                "Logout",
                Toast.LENGTH_LONG
            ).show()
            findNavController().navigate(R.id.action_addFragment_to_loginFragment)
        }
    }

    private fun observeAddCar(marca: String, description: String) {
        viewModel.addCar(marca, description).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    Toast.makeText(
                        requireContext(),
                        "Uploading car...",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Result.Success -> {
                    findNavController().navigate(R.id.action_addFragment_to_homeFragment)
                }
                is Result.Failure -> {
                    Toast.makeText(
                        requireContext(),
                        "Error ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}