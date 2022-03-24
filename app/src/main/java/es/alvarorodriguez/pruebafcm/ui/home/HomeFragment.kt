package es.alvarorodriguez.pruebafcm.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import es.alvarorodriguez.pruebafcm.R
import es.alvarorodriguez.pruebafcm.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        binding.logout.setOnClickListener {
            firebaseAuth.signOut()
            Toast.makeText(
                requireContext(),
                "Logout",
                Toast.LENGTH_LONG
            ).show()
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
    }
}