package es.alvarorodriguez.pruebafcm.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import es.alvarorodriguez.pruebafcm.R
import es.alvarorodriguez.pruebafcm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var preferences: SharedPreferences
    private var itemOlvidar: MenuItem? = null
    private var itemCambiar: MenuItem? = null
    private var itemLogout: MenuItem? = null

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        preferences = getPreferences(Context.MODE_PRIVATE)

        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        observeToken()
    }

    private fun observeDestinationChange() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                    itemOlvidar?.isVisible = false
                    itemCambiar?.isVisible = false
                    itemLogout?.isVisible = false
                }
                R.id.registerFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                    itemOlvidar?.isVisible = false
                    itemCambiar?.isVisible = false
                    itemLogout?.isVisible = false
                }
                else -> {
                    binding.bottomNavigationView.visibility = View.VISIBLE
                    itemOlvidar?.isVisible = true
                    itemCambiar?.isVisible = true
                    itemLogout?.isVisible = true
                }
            }
        }
    }

    private fun logout() {
        firebaseAuth.signOut()
        Toast.makeText(
            this,
            "Logout",
            Toast.LENGTH_LONG
        ).show()
        navController.navigate(R.id.loginFragment)
    }

    private fun observeToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = getString(R.string.msg_token_fmt, token)
            Log.d(TAG, msg)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        itemOlvidar = menu?.findItem(R.id.menuOlvidar)
        itemCambiar = menu?.findItem(R.id.menuCambiarTheme)
        itemLogout = menu?.findItem(R.id.logout)

        val nightMode = AppCompatDelegate.getDefaultNightMode()

        if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            itemCambiar?.icon =
                ContextCompat.getDrawable(applicationContext, R.drawable.ic_baseline_wb_sunny_24)
        } else {
            itemCambiar?.icon =
                ContextCompat.getDrawable(applicationContext, R.drawable.ic_baseline_bedtime_24)
        }
        observeDestinationChange()
        return true
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.menuOlvidar -> {
            preferences.edit().clear().apply()
            firebaseAuth.signOut()
            navController.navigate(R.id.loginFragment)
            true
        }
        R.id.menuCambiarTheme -> {
            val nightMode = AppCompatDelegate.getDefaultNightMode()

            if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            true
        }
        R.id.logout -> {
            logout()
            true
        }
        else -> false
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}