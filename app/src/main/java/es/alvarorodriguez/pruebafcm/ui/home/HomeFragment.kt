package es.alvarorodriguez.pruebafcm.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import es.alvarorodriguez.pruebafcm.R
import es.alvarorodriguez.pruebafcm.core.Result
import es.alvarorodriguez.pruebafcm.data.remote.home.HomeDataSource
import es.alvarorodriguez.pruebafcm.databinding.FragmentHomeBinding
import es.alvarorodriguez.pruebafcm.domain.home.HomeRepoImpl
import es.alvarorodriguez.pruebafcm.presentation.home.HomeViewModel
import es.alvarorodriguez.pruebafcm.presentation.home.HomeViewModelFactory
import es.alvarorodriguez.pruebafcm.ui.home.adapter.HomeAdapter

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel by viewModels<HomeViewModel> { HomeViewModelFactory(
        HomeRepoImpl(
            HomeDataSource()
        ))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        observerCars()
    }

    private fun observerCars() {
        viewModel.getCars().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.rvHome.adapter = HomeAdapter(result.data)
                    binding.progressBar.visibility = View.GONE
                }
                is Result.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        context,
                        "Error ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}