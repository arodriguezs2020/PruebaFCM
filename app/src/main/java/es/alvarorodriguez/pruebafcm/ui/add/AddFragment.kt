package es.alvarorodriguez.pruebafcm.ui.add

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import es.alvarorodriguez.pruebafcm.R
import es.alvarorodriguez.pruebafcm.core.Result
import es.alvarorodriguez.pruebafcm.data.remote.add.AddDataSource
import es.alvarorodriguez.pruebafcm.databinding.FragmentAddBinding
import es.alvarorodriguez.pruebafcm.domain.add.AddRepoImpl
import es.alvarorodriguez.pruebafcm.presentation.add.AddViewModel
import es.alvarorodriguez.pruebafcm.presentation.add.AddViewModelFactory

class AddFragment : Fragment(R.layout.fragment_add) {

    private lateinit var binding: FragmentAddBinding
    private var bitmap: Bitmap? = null

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageBitmap = result.data?.extras?.get("data") as Bitmap
                binding.imgAddPhoto.setImageBitmap(imageBitmap)
                bitmap = imageBitmap
            }
        }

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
        addPhoto()
        addCar()
    }

    private fun addPhoto() {
        binding.imgAddPhoto.setOnClickListener {
            try {
                val camara = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startForResult.launch(camara)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    requireContext(),
                    "No se encontró ninguna app para abrir la camara.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun addCar() {
        binding.btnAdd.setOnClickListener {
            bitmap?.let {
                val marca = binding.editTextMarca.text.toString().trim()
                val description = binding.editTextDescription.text.toString().trim()
                val imgCar = bitmap
                if (validateCredentials(marca, description)) {
                    observeAddCar(marca, description, imgCar!!)
                }
            }
        }
    }

    private fun validateCredentials(marca: String, description: String): Boolean {
        if (marca.isEmpty()) {
            binding.editTextMarca.error = "Brand is empty"
            return false
        }
        if (description.isEmpty()) {
            binding.editTextDescription.error = "Description is empty"
            return false
        }
        return true
    }


    private fun observeAddCar(marca: String, description: String, imgCar: Bitmap) {
        viewModel.addCar(marca, description, imgCar).observe(viewLifecycleOwner) { result ->
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